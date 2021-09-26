///usr/bin/env jbang "$0" "$@" ; exit $?
//REPOS mavencentral,jitpack
//DEPS com.github.opt-nc:colisnc-sdk:1.18
//DEPS info.picocli:picocli:4.5.0
//DEPS de.vandermeer:asciitable:0.3.2
import com.adriens.github.colisnc.colisnc.ColisCrawler;
import com.adriens.github.colisnc.colisnc.ColisDataRow;

import java.util.ArrayList;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.util.concurrent.Callable;

@Command(name = "colisnc", mixinStandardHelpOptions = true, version = "Colis-nc 0.1",
        description = "Le CLI pour suivre ses colis en Nouvelle-Calédonie")
class nope implements Callable<Integer> {

    //TODO ajouter valeur par défaut
    @CommandLine.Option(
            names = {"-c", "--colis-id"},
            description = "Le numéro du colis",
            required = true)
    private String colisId;

    public static void main(String... args) {
        int exitCode = new CommandLine(new nope()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() throws Exception { // your business logic goes here...
        //Excuses excuses = new Excuses();
        //System.out.println(excuses.pickRandomly(category));
        ArrayList<ColisDataRow> colisdDetails = ColisCrawler.getColisRows(colisId);
        System.out.println("Got <" + colisdDetails.size() + "> details pour <" + colisId + ">");
        ColisDataRow row = ColisCrawler.getLatest(colisId);
        System.out.println(row);
        return 0;
    }
}
