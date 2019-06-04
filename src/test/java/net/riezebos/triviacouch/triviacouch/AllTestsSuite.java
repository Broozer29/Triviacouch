package net.riezebos.triviacouch.triviacouch;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import net.riezebos.triviacouch.triviacouch.core.factories.AntwoordFactoryTest;
import net.riezebos.triviacouch.triviacouch.core.factories.SpelSessie;
import net.riezebos.triviacouch.triviacouch.core.factories.SpelerFactoryTest;
import net.riezebos.triviacouch.triviacouch.core.factories.VraagFactoryTest;
import net.riezebos.triviacouch.triviacouch.core.util.InitDB;
import net.riezebos.triviacouch.triviacouch.oud.TestTest;
import net.riezebos.triviacouch.triviacouch.oud.TestTest2;

@RunWith(Suite.class)
@SuiteClasses({InitDBTest.class, SpelSessie.class})

public class AllTestsSuite {

}
