package org.phpmaven.test;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.junit.jupiter.api.extension.ExtendWith;

/**
 * @author Stef Schulz <s.schulz@slothsoft.de>
 */

@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(IgnoreWhenExtension.class)
public @interface IgnoreWhen {

	Class<? extends IgnoreWhenCondition> value();
}