JAVAGRAMMAR = ../BytecodeBackend
COMPILER = $(PWD)/../Java1.5Frontend

FILES = StaticImports.ast StaticImports.jrag \
        EnhancedFor.ast EnhancedFor.jrag EnhancedForToBytecode.jrag \
        Generics.ast Generics.jrag GenericsPrettyPrint.jrag 
        
PARSEFILES = EnhancedFor.parser StaticImports.parser Generics.parser

export EXTERNALJRAGFILES += $(addprefix $(COMPILER)/, $(FILES))
export EXTERNALPARSERFILES += $(addprefix $(COMPILER)/, $(PARSEFILES))
export BUILDDIR = $(PWD)
export JAVACLASSPATH += $(PWD):

.PHONY: all clean

all:
	$(MAKE) -C $(JAVAGRAMMAR)

clean:
	$(MAKE) -C $(JAVAGRAMMAR) clean 

jar: all
	jar -cmf manifest JavaCompiler.jar `find . -name "*.class"` `find . -name "*.spec"`
