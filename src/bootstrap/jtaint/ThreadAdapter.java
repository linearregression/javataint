/*
 *  Copyright 2009-2012 Michael Dalton
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package jtaint;

import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

/** Instrument java.lang.Thread to store information about the current servlet
 * request. Allows other class to get/set this information during servlet 
 * processing.
 */

public class ThreadAdapter extends ClassAdapter implements Opcodes
{
    private String className;

    public ThreadAdapter(ClassVisitor cv) {
        super(cv);
    }

    public void visit(int version, int access, String name, 
                                String signature, String superName, 
                                String[] interfaces) 
    {
        className = name;
        cv.visit(version, access, name, signature, superName, interfaces);
    }

    private void addField(String fieldName, String getterName, 
                          String setterName, String desc) 
    {
        String internalField = ByteCodeUtil.internalName(fieldName);
        cv.visitField(ACC_PRIVATE + ACC_TRANSIENT, 
                      internalField, desc, null, null).visitEnd();
        ByteCodeUtil.buildGetter(cv, className, internalField, desc, ACC_FINAL,
                                 ByteCodeUtil.internalName(getterName));
        ByteCodeUtil.buildSetter(cv, className, internalField, desc, ACC_FINAL,
                                 ByteCodeUtil.internalName(setterName));
    }

    public void visitEnd() {
        addField("remoteAddr", "getRemoteAddr", "setRemoteAddr",
                 "Ljava/lang/String;");
        addField("remoteHost", "getRemoteHost", "setRemoteHost", 
                 "Ljava/lang/String;");
        addField("requestParams", "getRequestParams", "setRequestParams",
                "Ljava/util/Map;");
        cv.visitEnd();
    }

    public static InstrumentationBuilder builder() { 
        return Builder.getInstance(); 
    }

    private static class Builder implements InstrumentationBuilder {
        private static final Builder b = new Builder();

        public static InstrumentationBuilder getInstance() { return b; }

        public ClassVisitor build(ClassVisitor cv) {
            return new ThreadAdapter(cv);
        }
    }
}
