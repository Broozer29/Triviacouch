package net.riezebos.triviacouch;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import net.riezebos.triviacouch.util.InitDBTest;

//Tests staan uit zodat de console niet overspoelt wordt.
//AntwoordDaoTest.class, SpelerDaoTest.class, VraagDaoTest.class, FullGameTest.class 
@RunWith(Suite.class)
@SuiteClasses({ InitDBTest.class, FullGameTest.class })

public class AllTestsSuite {

}
