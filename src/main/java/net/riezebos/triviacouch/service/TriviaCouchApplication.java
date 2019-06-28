package net.riezebos.triviacouch.service;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
/*
 * Deze functie wordt gebruikt zodat er niet voor elke /path() een "api" hoeft te staan.
 */

@ApplicationPath("api")
public class TriviaCouchApplication extends Application {

}
