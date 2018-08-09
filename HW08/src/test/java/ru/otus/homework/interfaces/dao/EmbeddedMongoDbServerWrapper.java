package ru.otus.homework.interfaces.dao;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;

import java.io.IOException;

public class EmbeddedMongoDbServerWrapper {
    private String host;
    private int port;

    private MongodExecutable mongodExecutable;
    private MongodProcess mongodProcess;

    public EmbeddedMongoDbServerWrapper(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws IOException {
        MongodStarter starter = MongodStarter.getDefaultInstance();
        IMongodConfig mongodConfig = new MongodConfigBuilder()
                .version(Version.Main.PRODUCTION)
                .net(new Net(host, port, Network.localhostIsIPv6()))
                .build();
        mongodExecutable = starter.prepare(mongodConfig);
        mongodProcess = mongodExecutable.start();
    }

    public void stop() {
        if (mongodProcess != null) {
            mongodProcess.stop();
        }
        if (mongodExecutable != null) {
            mongodExecutable.stop();
        }
    }
}
