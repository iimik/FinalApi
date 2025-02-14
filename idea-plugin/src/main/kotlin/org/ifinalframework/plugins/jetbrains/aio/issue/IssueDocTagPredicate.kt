package org.ifinalframework.plugins.jetbrains.aio.issue;

import com.intellij.psi.PsiElement
import java.util.function.Predicate


/**
 * Java
 *
 * @author iimik
 * @since 0.0.1
 **/
interface IssueDocTagPredicate : Predicate<PsiElement> {
}