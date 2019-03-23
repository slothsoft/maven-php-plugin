package org.phpmaven.test;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.junit.jupiter.api.extension.ExtendWith;

/**
 * @author <a href="mailto:s.schulz@slothsoft.de">Stef Schulz</a>
 */

@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(IgnoreWhenExtension.class)
public @interface IgnoreWhen {

	Class<? extends IgnoreWhenCondition> value();
}