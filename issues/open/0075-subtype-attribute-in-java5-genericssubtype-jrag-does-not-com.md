# subtype-attribute in java5/GenericsSubtype.jrag does not compute subtype for type variables

There is an attribute in the file java5/frontend/GenericsSubtype.jrag called subtype. This attribute does not really compute the subtype for type variables though, but checks that a certain type is legal to use as substitute for that type variable. It would make more sense to not name this attribute subtype, but maybe "canSubstitute" or something similar, and then have a separate attribute which actually computes the subtype for all types.
