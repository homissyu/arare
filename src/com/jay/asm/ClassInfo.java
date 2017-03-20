/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jay.asm;

import java.io.IOException;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;

/**
 *
 * @author Karl
 */
public class ClassInfo implements Opcodes{
    public static void main(final String[] args) throws IOException{
        ClassReader cr = new ClassReader("java.sql.Connection");
        ClassNode cn = new ClassNode();
        cr.accept(cn, ClassReader.SKIP_CODE);
        System.out.println("ClassName:"+cn.name);
        System.out.println("SupperClass:"+cn.superName);
    }
}
