''' Jython module that uses Claw Java transformation classes to transform
    an (OMNI-produced) XcodeML AST '''

def walk(xnode, level=0):
    cnode = xnode.firstChild()
    while cnode:
        print level*"  ", cnode.toString()
        walk(cnode, level+1)
        cnode = cnode.nextSibling()

def claw_trans(xast):
    '''
    '''
    from claw.tatsu.primitive import Loop
    from claw.tatsu.xcodeml.xnode.common import Xcode
    from claw.tatsu.xcodeml.xnode import XnodeUtil
    walk(xast)
    node = xast.firstChild()
    print type(node)
    do_loops = xast.matchAll(Xcode.F_DO_STATEMENT)
    print "Found {0} do loops".format(len(do_loops))
    pragmas = xast.matchAll(Xcode.F_PRAGMA_STATEMENT)
    print "Found {0} pragmas".format(len(pragmas))
    for pragma in pragmas:
        # If this is a CLAW pragma then delete it
        if "claw" in pragma.value().lower():
            XnodeUtil.safeDelete(pragma)
    return xast
