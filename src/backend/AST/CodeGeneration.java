/* Copyright (c) 2005-2008, Torbjorn Ekman
 *                    2013, Jesper Öqvist <jesper.oqvist@cs.lth.se>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright notice,
 *       this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the Lund University nor the names of its
 *       contributors may be used to endorse or promote products derived from
 *       this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package AST;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

@SuppressWarnings({"javadoc","unchecked","rawtypes"})
class CodeGeneration {
	private ByteArray bytes = new ByteArray();
	private ConstantPool constantPool;

	public void clearCodeGeneration() {
		bytes = null;
		constantPool = null;
		variableScopeLabelAddress = null;
		variableScopeLabelUses = null;
		localVariableTable = null;
		lineNumberTable = null;
		exceptions = null;
		address = null;
		uses = null;
	}

	private boolean wideGoto = false;

	private boolean numberFormatError = false;
	public boolean numberFormatError() { return numberFormatError; }

	public CodeGeneration(ConstantPool constantPool) {
		this.constantPool = constantPool;
	}

	public CodeGeneration(ConstantPool constantPool, boolean wideGoto) {
		this.constantPool = constantPool;
		this.wideGoto = wideGoto;
	}

	public ConstantPool constantPool() {
		return constantPool;
	}

	private int variableScopeLabel = 1;
	public int variableScopeLabel() {
		return variableScopeLabel++;
	}
	public void addVariableScopeLabel(int label) {
		Integer label_object = new Integer(label);
		variableScopeLabelAddress.put(label_object, new Integer(pos()));
		// Update all reference to this label
		if(variableScopeLabelUses.containsKey(label_object)) {
			ArrayList array = (ArrayList)variableScopeLabelUses.get(label_object);
			for(Iterator iter = array.iterator(); iter.hasNext(); ) {
				LocalVariableEntry e = (LocalVariableEntry)iter.next();
				e.length = pos() - e.start_pc;
			}
		}
	}
	private HashMap variableScopeLabelAddress = new HashMap();
	private HashMap variableScopeLabelUses = new HashMap();

	static class LocalVariableEntry {
		int start_pc;
		int length;
		int name_index;
		int descriptor_index;
		int index;
	}

	public Collection localVariableTable = new ArrayList();
	public void addLocalVariableEntryAtCurrentPC(String name, String typeDescriptor, int localNum, int variableScopeEndLabel) {
		LocalVariableEntry e = new LocalVariableEntry();
		e.start_pc = pos();
		e.length = 0;
		e.name_index = constantPool().addUtf8(name);
		e.descriptor_index = constantPool().addUtf8(typeDescriptor);
		e.index = localNum;
		localVariableTable.add(e);
		Integer label_object = new Integer(variableScopeEndLabel);
		if(!variableScopeLabelUses.containsKey(label_object))
			variableScopeLabelUses.put(label_object, new ArrayList());
		Collection c = (Collection)variableScopeLabelUses.get(label_object);
		c.add(e);
	}

	static class LineNumberEntry {
		int start_pc;
		int line_number;
	}

	public Collection lineNumberTable = new ArrayList();
	public void addLineNumberEntryAtCurrentPC(ASTNode node) {
		LineNumberEntry e = new LineNumberEntry();
		e.start_pc = pos();
		e.line_number = node.sourceLineNumber();
		if(e.line_number != -1 && e.line_number != 65535)
			lineNumberTable.add(e);
	}

	public Collection exceptions = new ArrayList();

	/**
	 * Adds an exception handler for the given exception type.
	 * If catch_type is zero then this handler catches any exception.
	 * @param start_lbl
	 * @param end_lbl
	 * @param handler_lbl
	 * @param catch_type
	 */
	public void addException(int start_lbl, int end_lbl, int handler_lbl, int catch_type) {
		int start_pc = addressOf(start_lbl);
		int end_pc = addressOf(end_lbl);
		if (start_pc != end_pc) {
			exceptions.add(new ExceptionEntry(
						start_pc,
						end_pc,
						handler_lbl,
						catch_type));
		}
	}

	class ExceptionEntry {
		public static final int CATCH_ALL = 0;

		int start_pc;
		int end_pc;
		int handler_lbl;
		int catch_type;

		public ExceptionEntry(int start_pc, int end_pc, int handler_lbl, int catch_type) {
			this.start_pc = start_pc;
			this.end_pc = end_pc;
			this.handler_lbl = handler_lbl;
			this.catch_type = catch_type;
		}

		public int handlerPC() {
			return addressOf(handler_lbl);
		}
	}

	public int maxLocals() {
		return maxLocals+1;
	}

	int maxLocals = 0;

	private HashMap address = new HashMap();

	private HashMap uses = new HashMap();

	public void addLabel(int label) {
		Integer label_object = new Integer(label);
		address.put(label_object, new Integer(pos()));
		// Update all reference to this label
		if(uses.containsKey(label_object)) {
			ArrayList array = (ArrayList)uses.get(label_object);
			for(int i = 0; i < array.size(); i++) {
				int p = ((Integer)array.get(i)).intValue();
				if(bytes.get(p) == Bytecode.GOTO_W)
					setAddress32(p + 1, pos() - p);
				else
					setAddress(p + 1, pos() -  p);
			}
		}
	}

	public int addressOf(int label) {
		Integer label_object = new Integer(label);
		if (!address.containsKey(label_object)) {
			throw new Error("Can not compute address of unplaced label (id: " +
					label + ")");
		}
		return ((Integer) address.get(label_object)).intValue();
	}

	private int jump(int label) {
		Integer label_object = new Integer(label);
		if (!uses.containsKey(label_object)) {
			uses.put(label_object, new ArrayList());
		}
		ArrayList a = (ArrayList)uses.get(label_object);
		a.add(new Integer(pos())); // position of the 16-bits reference
		Integer val = (Integer)address.get(label_object);
		if (val != null) {
			return val.intValue() - pos();
		}
		return 0; // a position of 0 means not calculated yet
	}

	private void setAddress(int position, int address) {
		if(address > Short.MAX_VALUE || address < Short.MIN_VALUE)
			numberFormatError = true;
		bytes.set(position + 0, (byte)((address&0xff00)>>8));
		bytes.set(position + 1, (byte)(address&0xff));
	}

	private void setAddress32(int position, int address) {
		bytes.set(position + 0, (byte)(address >> 24 & 0xff));
		bytes.set(position + 1, (byte)(address >> 16 & 0xff));
		bytes.set(position + 2, (byte)(address >> 8 & 0xff));
		bytes.set(position + 3, (byte)(address & 0xff));
	}

	public void emitStoreReference(int pos) {
		maxLocals = Math.max(maxLocals, pos+1);
		if(pos == 0) emit(Bytecode.ASTORE_0);
		else if(pos == 1) emit(Bytecode.ASTORE_1);
		else if(pos == 2) emit(Bytecode.ASTORE_2);
		else if(pos == 3) emit(Bytecode.ASTORE_3);
		else if(pos < 256) emit(Bytecode.ASTORE).add(pos);
		else emit(Bytecode.WIDE).emit(Bytecode.ASTORE).add2(pos);
	}

	public void emitLoadReference(int pos) {
		maxLocals = Math.max(maxLocals, pos+1);
		if(pos == 0) emit(Bytecode.ALOAD_0);
		else if(pos == 1) emit(Bytecode.ALOAD_1);
		else if(pos == 2) emit(Bytecode.ALOAD_2);
		else if(pos == 3) emit(Bytecode.ALOAD_3);
		else if(pos < 256) emit(Bytecode.ALOAD).add(pos);
		else emit(Bytecode.WIDE).emit(Bytecode.ALOAD).add2(pos);
	}

	public void emitReturn() {
		bytes.emit(Bytecode.RETURN);
	}

	public void emitThrow() {
		bytes.emit(Bytecode.ATHROW);
	}

	public void emitInstanceof(TypeDecl type) {
		int p = constantPool().addClass(type.isArrayDecl() ? type.typeDescriptor() : type.constantPoolName());
		bytes.emit(Bytecode.INSTANCEOF).add2(p);
	}

	public void emitCheckCast(TypeDecl type) {
		int p = constantPool().addClass(type.isArrayDecl() ? type.typeDescriptor() : type.constantPoolName());
		bytes.emit(Bytecode.CHECKCAST).add2(p);
	}

	public void emitDup() {
		bytes.emit(Bytecode.DUP);
	}

	public void emitDup2() {
		bytes.emit(Bytecode.DUP2);
	}

	public void emitPop() {
		bytes.emit(Bytecode.POP);
	}

	public void emitSwap() {
		bytes.emit(Bytecode.SWAP);
	}

	public void emitBranchNonNull(int label) {
		int p = jump(label);
		bytes.emit(Bytecode.IFNONNULL).add2(p);
	}

	public void emitGoto(int label) {
		int p = jump(label);
		if(wideGoto)
			bytes.emitGoto(Bytecode.GOTO_W).add4(p);
		else {
			if(p > Short.MAX_VALUE || p < Short.MIN_VALUE)
				numberFormatError = true;
			bytes.emitGoto(Bytecode.GOTO).add2(p);
		}
	}

	public void emitCompare(byte bytecode, int label) {
		int p = jump(label);
		bytes.emit(bytecode).add2(p);
	}

	@Override
	public String toString() {
		return bytes.toString();
	}

	public int size() {
		return bytes.size();
	}

	public int pos() {
		return bytes.pos();
	}

	public void setPos(int pos) {
		bytes.setPos(pos);
	}

	public void skip(int num) {
		bytes.skip(num);
	}

	public byte[] toArray() {
		return bytes.toArray();
	}

	CodeGeneration add(int i) {
		return add((byte)i);
	}

	CodeGeneration add(byte b) {
		bytes.add(b);
		return this;
	}

	CodeGeneration add2(int index) {
		bytes.add2(index);
		return this;
	}

	CodeGeneration add4(int index) {
		bytes.add4(index);
		return this;
	}

	CodeGeneration emit(byte b) {
		bytes.emit(b);
		return this;
	}

	CodeGeneration emit(byte b, int stackChange) {
		bytes.emit(b, stackChange);
		return this;
	}

	public int maxStackDepth() {
		return bytes.maxStackDepth();
	}

	public int stackDepth() {
		return bytes.stackDepth();
	}

	public void changeStackDepth(int i) {
		bytes.changeStackDepth(i);
	}

	static class ExceptionRange {
		int start;
		int end;

		ExceptionRange(int start, int end) {
			this.start = start;
			this.end = end;
		}
	}

	static class Monitor {
		java.util.List<ExceptionRange> ranges =
			new ArrayList<ExceptionRange>();
		final SynchronizedStmt mon;
		int start_lbl = -1;

		Monitor(SynchronizedStmt mon) {
			this.mon = mon;
		}

		void rangeStart(int label) {
			start_lbl = label;
		}

		void rangeEnd(int label) {
			if (start_lbl != -1) {
				ranges.add(new ExceptionRange(start_lbl, label));
				start_lbl = -1;
			}
		}

		void monitorEnter(CodeGeneration gen) {
			gen.emitDup();
			gen.emitStoreReference(mon.localNum());
			gen.emit(Bytecode.MONITORENTER);
		}

		void monitorExit(CodeGeneration gen) {
			MonitorExit monExit = mon.getMonitorExit();
			monExit.emitMonitorExitHandler(gen);
			if (start_lbl != -1 && gen.addressOf(start_lbl) !=
					gen.addressOf(monExit.handler_label())) {
				rangeEnd(monExit.handler_end_label());
			}
			for (ExceptionRange range: ranges) {
				gen.addException(range.start, range.end,
						monExit.handler_label(),
						CodeGeneration.ExceptionEntry.CATCH_ALL);
			}
		}
	}

	java.util.List<Monitor> monitors = new ArrayList<Monitor>();

	/**
	 * Pus a new monitor to the monitor stack.
	 * @param mon the monitor local number
	 * @return the monitor id
	 */
	public int monitorEnter(SynchronizedStmt mon) {
		Monitor monitor = new Monitor(mon);
		monitor.monitorEnter(this);
		monitors.add(monitor);
		return monitors.size()-1;
	}

	/**
	 * Exit the current top monitor.
	 */
	public void monitorExit() {
		if (monitors.isEmpty())
			throw new Error("Monitor stack is empty!");
		Monitor monitor = monitors.remove(monitors.size()-1);
		monitor.monitorExit(this);
	}

	/**
	 * Start a monitor exception range.
	 * @param monitorId the monitor id
	 */
	public void monitorRangeStart(int monitorId, int label) {
		monitors.get(monitorId).rangeStart(label);
	}

	public void monitorRangesStart(Stmt branch, int label) {
		for (Monitor monitor: monitors) {
			if (branch.leavesMonitor(branch, monitor.mon)) {
				monitor.rangeStart(label);
			}
		}
	}

	/**
	 * End a monitor exception range.
	 * @param monitorId the monitor id
	 */
	public void monitorRangeEnd(int monitorId, int label) {
		Monitor monitor = monitors.get(monitorId);
		emitLoadReference(monitor.mon.localNum());
		emit(Bytecode.MONITOREXIT);
		addLabel(label);
		monitor.rangeEnd(label);
	}
}

