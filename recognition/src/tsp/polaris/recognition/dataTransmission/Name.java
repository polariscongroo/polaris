package tsp.polaris.recognition.dataTransmission;

public class Name {

    private String[] names;

    public Name(){
        String[] constellation_names = new String[] { 
            "andromeda", "antlia", "apus", "aquarius", "aquila", "ara", "aries", "auriga", "boötes",
            "caelum", "camelopardalis", "cancer", "canis_major", "canis_minor",
            "capricornus", "carina", "cassiopeia", "centaurus", "cepheus", "cetus", "chamaeleon",
            "circinus", "columba", "corvus", "crater", "crux", "cygnus", "delphinus", "dorado",
            "draco", "equuleus", "eridanus", "fornax", "gemini", "grus", "hercules",
            "horologium", "hydra", "hydrus", "indus", "lacerta", "leo", "leo_minor", "lepus",
            "libra", "lupus", "lynx", "lyra", "mensa", "microscopium", "monoceros",
            "musca", "norma", "octans", "ophiuchus", "orion", "pavo", "pegasus", "perseus",
            "phoenix", "pictor", "pisces", "puppis", "pyxis", "reticulum", "sagitta",
            "sagittarius", "scorpius", "sculptor", "scutum", "serpens", "sextans", "taurus",
            "telescopium", "triangulum", "tucana", "ursa_major", "ursa_minor", "vela",
            "virgo", "volans", "vulpecula"
        };        
        names = constellation_names;
    }

}
