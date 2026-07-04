# Simple template build script for extensions

**Status:** resolved

The JastAddJ build scripts become more and more complicated for each Java version because each Java module must explicitly include the source files from previous modules.

It would be great if we could make a template build script for people who wish to extend JastAddJ.

One possible approach would be to include the build.xml for the target Java module and then set some properties to specify extension source files using [XML external entities](http://www.w3.org/TR/REC-xml/#sec-external-ent).

## Comments

### Jesper Öqvist - 2013-06-18

Relevant link: [Ant FAQ: How do I include an XML snippet in my build file?](http://ant.apache.org/faq.html#xml-entity-include)

### Jesper Öqvist - 2013-06-18

First extension build script draft:

````ant
<project name="JastAddJ Extension Example" default="build">
	<property name="jj.root" location="${basedir}/../jastaddj"/>
	<property name="bin.dir" location="bin"/>

	<property name="extension.rags.dir" location="jastadd"/>
	<property name="extension.src.dir" location="src"/>

	<import file="${jj.root}/java7/build.xml"/>
</project>
````

### Jesper Öqvist - 2016-02-24

The [Extendsion Base project](https://bitbucket.org/extendj/extension-base) is a useful template start project for building ExtendJ extensions.
