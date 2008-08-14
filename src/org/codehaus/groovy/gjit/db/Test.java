package org.codehaus.groovy.gjit.db;

public class Test {

    public static void main(String[] args) throws Throwable {
        SiteTypePersistentCache.v().add("a/b/c/Test",10L)
        .add(0, "V")
        .add(1, "I")
        .add(2, "B")
        .add(3, "J");
        // SiteTypePersistentCache.read();
        ClassEntry c = SiteTypePersistentCache.v().find("a/b/c/Test");
        System.out.println(c.getTimeStamp());
    }

}
