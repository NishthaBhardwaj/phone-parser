package com.navvish.api.automation;

import com.intuit.karate.junit5.Karate;
import com.intuit.karate.junit5.Karate.Test;

public class TesPostRunner {

    @Test
    public Karate runTest(){
        return Karate.run("createUser").relativeTo(getClass());


    }
}
