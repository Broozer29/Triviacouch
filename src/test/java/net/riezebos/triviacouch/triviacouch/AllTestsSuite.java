package net.riezebos.triviacouch.triviacouch;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import net.riezebos.triviacouch.triviacouch.core.StartScherm;

@RunWith(Suite.class)
@SuiteClasses({InitDBTest.class, StartScherm.class})

public class AllTestsSuite {

}
