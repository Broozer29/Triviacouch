package net.riezebos.triviacouch.triviacouch;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import net.riezebos.triviacouch.triviacouch.core.factories.InitDB;
import net.riezebos.triviacouch.triviacouch.core.factories.SpelerFactoryTest;

@RunWith(Suite.class)
@SuiteClasses({InitDBTest.class, SpelerFactoryTest.class, TestTest.class, TestTest2.class })

public class AllTestsSuite {

}
