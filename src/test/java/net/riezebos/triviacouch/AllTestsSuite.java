package net.riezebos.triviacouch;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import net.riezebos.triviacouch.persistence.AntwoordDaoTest;
import net.riezebos.triviacouch.persistence.DeelnemerDaoTest;
import net.riezebos.triviacouch.persistence.SpelerDaoTest;
import net.riezebos.triviacouch.persistence.VraagDaoTest;
import net.riezebos.triviacouch.util.InitDBTest;

//Tests staan uit zodat de console niet overspoelt wordt.
//AntwoordDaoTest.class, SpelerDaoTest.class, VraagDaoTest.class, FullGameTest.class 
@RunWith(Suite.class)
@SuiteClasses({ InitDBTest.class, FullGameTest.class  })

public class AllTestsSuite {

}
