package net.riezebos.triviacouch;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import net.riezebos.triviacouch.persistence.AntwoordDaoTest;
import net.riezebos.triviacouch.persistence.SpelerDaoTest;
import net.riezebos.triviacouch.persistence.VraagDaoTest;
import net.riezebos.triviacouch.util.InitDBTest;

@RunWith(Suite.class)
@SuiteClasses({ InitDBTest.class, FullGameTest.class, AntwoordDaoTest.class, SpelerDaoTest.class, VraagDaoTest.class })

public class AllTestsSuite {

}
