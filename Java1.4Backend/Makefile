JAVAGRAMMAR = ../Java1.4Frontend
BACKEND = $(PWD)/../BytecodeBackend

JRAGBACKENDFILES = constantpool.ast \
ConstantPool.jrag \
ConstantPoolNames.jrag \
Preparation.ast \
Preparation.jrag \
CreateBCode.jrag \
LocalNum.jrag \
PrettyPrintBCode.jrag \
Attributes.jrag \
CodeGeneration.jrag \
JVMBytecodes.jrag \
Flags.jrag \
GenerateClassfile.jrag \
InnerClasses.jrag


JAVABACKENDFILES = GenerateBCodefile.java

export EXTERNALJRAGFILES += $(addprefix $(BACKEND)/, $(JRAGBACKENDFILES))
export EXTERNALJAVAFILES += $(addprefix $(BACKEND)/, $(JAVABACKENDFILES))
export BUILDDIR = $(PWD)
export JAVACLASSPATH += $(PWD):

.PHONY: all clean

all:
	$(MAKE) -C $(JAVAGRAMMAR)

clean:
	$(MAKE) -C $(JAVAGRAMMAR) clean 
