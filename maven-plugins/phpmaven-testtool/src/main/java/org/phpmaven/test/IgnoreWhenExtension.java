package org.phpmaven.test;

import static org.junit.jupiter.api.extension.ConditionEvaluationResult.disabled;
import static org.junit.jupiter.api.extension.ConditionEvaluationResult.enabled;
import static org.junit.platform.commons.util.AnnotationUtils.findAnnotation;

import java.util.Optional;

import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;

public class IgnoreWhenExtension implements ExecutionCondition {

	private static final ConditionEvaluationResult ENABLED_BY_DEFAULT = enabled("@IgnoreWhen is not present");
	private static final ConditionEvaluationResult ENABLED =
			enabled("Enabled because IgnoreWhenCondition returned true.");
	private static final ConditionEvaluationResult DISABLED =
			disabled("Disabled because IgnoreWhenCondition returned false.");

	@Override
	public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
		final Optional<IgnoreWhen> optional = findAnnotation(context.getElement(), IgnoreWhen.class);
		if (optional.isPresent()) {
			final Class<? extends IgnoreWhenCondition> ignoreWhenConditionClass = optional.get().value();
			try {
				final IgnoreWhenCondition ignoreWhenCondition = ignoreWhenConditionClass.newInstance();
				return ignoreWhenCondition.isDisabled() ? DISABLED : ENABLED;
			} catch (final InstantiationException e) {
				throw new RuntimeException("Could not instantiate " + ignoreWhenConditionClass, e);
			} catch (final IllegalAccessException e) {
				throw new RuntimeException("Could not instantiate " + ignoreWhenConditionClass, e);
			}
		}
		return ENABLED_BY_DEFAULT;
	}
}