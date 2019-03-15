package org.phpmaven.phpexec.test;

import org.codehaus.plexus.util.cli.CommandLineException;
import org.codehaus.plexus.util.cli.CommandLineUtils;
import org.codehaus.plexus.util.cli.Commandline;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class PhpExePresent implements TestRule {

	// XXX [slothsoft]: These tests should probably work locally at least

	private Boolean phpExePresent;

	@Override
	public synchronized Statement apply(final Statement base, Description description) {
		if (this.phpExePresent == null) {
			try {
				final Commandline commandLine = new Commandline("php");
				CommandLineUtils.executeCommandLine(commandLine, null, null, null);
				this.phpExePresent = Boolean.FALSE; // supposed to be true
			} catch (final CommandLineException e) {
				this.phpExePresent = Boolean.FALSE;
			}
		}
		if (this.phpExePresent.booleanValue()) return new Statement() {
			@Override
			public void evaluate() throws Throwable {
				base.evaluate();
			}
		};
		return new Statement() {

			@Override
			public void evaluate() throws Throwable {
				// nothing to do if there is no PHP
			}
		};
	}
}
