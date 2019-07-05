package com.example.plugin;




import com.sun.source.tree.BlockTree;
import com.sun.source.tree.ClassTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.VariableTree;
import com.sun.source.util.JavacTask;
import com.sun.source.util.Plugin;
import com.sun.source.util.TaskEvent;
import com.sun.source.util.TaskListener;
import com.sun.source.util.TreeScanner;

public class SimplePlugin implements Plugin {

	@Override
	public String getName() {
		// this name will be used as value of -Xplugin
		// exp: javac -cp plugin.jar -Xplugin:SimplePlugin Test.java
		System.out.println("<getName/>");
		return "SimplePlugin";
	}

	@Override
	public void init(JavacTask task, String... arg1) {
		System.out.println("<init>");
		task.addTaskListener(new TaskListener() {

			@Override
			public void started(TaskEvent arg0) {
				System.out.println("<started>");
				System.out.println("Task kind: " + arg0.getKind().name());
				System.out.println("</started>");

			}

			@Override
			public void finished(TaskEvent arg0) {
				System.out.println("<finished>");
				if (arg0.getKind() != TaskEvent.Kind.PARSE) {
					return;
				}
				arg0.getCompilationUnit().accept(new TreeScanner<Void, Void>() {
					@Override
					public Void visitClass(ClassTree node, Void aVoid) {
						System.out.println("<visitClass>");
						System.out.println("Node type:" + node.getKind().name());
						node.getMembers().forEach(x -> System.out.println(x.getKind().name()));
						System.out.println("</visitClass>");
						
						return super.visitClass(node, aVoid);
					}
					@Override
					public Void visitMethod(MethodTree node, Void aVoid) {
						System.out.println("<visitMethod>");
						System.out.println("Node type:" + node.getKind().name() + " Method name: " + node.getName());
						System.out.println("</visitMethod>");
						return super.visitMethod(node, aVoid);
					}
					
					@Override
					public Void visitBlock(BlockTree arg0, Void arg1) {
						return super.visitBlock(arg0, arg1);
					}
					@Override
					public Void visitVariable(VariableTree arg0, Void arg1) {
						return super.visitVariable(arg0, arg1);
					}
					
					
				}, null);
				System.out.println("</finished>");

			}
		});

		System.out.println("</init>");
	}



}
