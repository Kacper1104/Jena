import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class Main {

    private static final String URI = "http://www.semanticweb.org/kacper/ontologies/2022/2/pizza#";
    private static final String FILE_PATH = "D:\\src\\java\\JENA\\src\\main\\ontologies\\pizza.owl";
    private static final String TARGET_PATH = "D:\\src\\java\\JENA\\src\\main\\ontologies\\pizza_jena.owl";

    public static void main(String[] args){
        OntModel model = createNewModel();
        readPizza(model, FILE_PATH);
        OntClass pizza = getClass(model, "kk_Pizza");
        OntClass sauce = addClass(model, "Sauce");
        setSuperClass(sauce, pizza);
        Property hasIngreedient = getProperty(model, "hasIngreedient");
        ObjectProperty hasSauce = addProperty(model, pizza, sauce, "hasSauce");
        setSuperProperty(hasSauce, hasIngreedient);
        saveModel(model, TARGET_PATH);
        printModel(model);
    }

    private static OntModel createNewModel(){
        return ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
    }
    private static void readPizza(Model model, String path){
        model.read("file:"+path);
    }
    private static void printModel(Model model) {
        model.write(System.out);
    }
    private static void saveModel(Model model, String path){
        try(PrintWriter printWriter = new PrintWriter(path)){
            model.write(printWriter);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private static OntClass getClass(OntModel model, String name){
        return model.getOntClass(URI+name);
    }

    private static Property getProperty(Model model, String name){
        return model.getProperty(URI+name);
    }

    private static OntClass addClass(OntModel model, String name){
        return model.createClass(URI+name);
    }

    private static void setSuperClass(OntClass ontClass, OntClass superClass){
        ontClass.addSuperClass(superClass);
    }

    private static ObjectProperty addProperty(OntModel model, OntClass domain, OntClass range, String name){
        ObjectProperty newProperty = model.createObjectProperty(URI+name);
        newProperty.addRange(range);
        newProperty.addDomain(domain);
        return newProperty;
    }

    private static void setSuperProperty(ObjectProperty property, Property superProperty){
        property.addSuperProperty(superProperty);
    }
}
