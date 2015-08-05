/*
 * Copyright (c) 2014-2015 University of Ulm
 *
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership.  Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package components.installer;

import de.uniulm.omi.cloudiator.sword.api.remote.RemoteConnection;

import play.Logger;
import play.Play;


/**
 * Created by Daniel Seybold on 19.05.2015.
 */
public class UnixInstaller extends AbstractInstaller {
    private final String homeDir;
    private final String javaArchive = "jre8.tar.gz";
    private final String javaDownload = Play.application().configuration().getString("colosseum.installer.linux.java.download");
    private final String dockerDownload = Play.application().configuration().getString("colosseum.installer.linux.lifecycle.docker.download");
    private final String dockerInstall = "docker_install.sh";

    public UnixInstaller(RemoteConnection remoteConnection, String user) {
        super(remoteConnection);

        this.homeDir = "/home/" + user;
    }

    @Override
    public void initSources() {

        //java
        this.sourcesList.add("wget " + this.javaDownload + " -O " + this.javaArchive);
        //docker
        this.sourcesList.add("wget " + this.dockerDownload + " -O " + this.dockerInstall);
        //kairosDB
        this.sourcesList.add("wget " + this.kairosDbDownload + " -O " + this.kairosDbArchive);
        //visor
        this.sourcesList.add("wget " + this.visorDownload + " -O " + this.visorJar);
    }



    @Override
    public void installJava() {

        Logger.debug("Starting Java installation...");
        //create directory
        this.remoteConnection.executeCommand("mkdir " + this.javaDir);
        //extract java
        this.remoteConnection.executeCommand("tar zxvf "+this.javaArchive+" -C "+this.javaDir+" --strip-components=1");
        //set symbolic link
        this.remoteConnection.executeCommand("sudo ln -s "+ this.homeDir + "/"+this.javaDir+"/bin/java /usr/bin/java");
        Logger.debug("Java was successfully installed!");
    }

    @Override
    public void installVisor() {

        Logger.debug("setting up Visor...");

        //create properties file
        this.remoteConnection.writeFile(this.homeDir + "/default.properties",this.buildDefaultVisorConfig(), false);

        //start visor
        this.remoteConnection.executeCommand("java -jar "+this.visorJar+" -conf default.properties &> /dev/null &");
        Logger.debug("Visor started successfully!");
    }

    @Override
    public void installKairosDb() {

        Logger.debug("Installing and starting KairosDB...");
        this.remoteConnection.executeCommand("mkdir " + this.kairosDbDir);

        this.remoteConnection.executeCommand("tar  zxvf "+this.kairosDbArchive+" -C "+ this.kairosDbDir +" --strip-components=1");

        this.remoteConnection.executeCommand(" sudo "+this.kairosDbDir+"/bin/kairosdb.sh start");
        Logger.debug("KairosDB started successfully!");
    }

    @Override
    public void installLifecycleAgent() {

        //install docker
        Logger.debug("Installing and starting LifecycleAgent:Docker...");
        this.remoteConnection.executeCommand("sudo chmod +x " + this.dockerInstall);
        this.remoteConnection.executeCommand("sudo ./" + this.dockerInstall);
        this.remoteConnection.executeCommand("sudo service docker restart");
        Logger.debug("LifecycleAgent:Docker installed and started successfully!");
    }

    @Override
    public void installAll() {

        Logger.debug("Starting installation of all tools on UNIX...");

        this.initSources();
        this.downloadSources();

        this.installJava();

        this.installLifecycleAgent();

        this.installKairosDb();

        this.installVisor();

        this.finishInstallation();


    }
}

