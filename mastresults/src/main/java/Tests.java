
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.epsilon.egl.EgxModule;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

public class Tests {
    
	/**
    public static void main(String[] args) throws Exception {
    	
    	genGraficas();
    }**/
    
    @BeforeAll
	static void genGraficas() throws Exception {
    	// Registrar los parsers xmi y metamodelos
        Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
        ResourceSet ecoreResourceSet = new ResourceSetImpl();
        
        String[] ecoreFiles = { "metamodelos/Mast2.ecore", "metamodelos/Mast2_Results.ecore" };
		for (String ecoreFile : ecoreFiles) {
			System.out.println(ecoreFile);
			Resource ecoreResource = ecoreResourceSet.createResource(
					URI.createFileURI(new File(ecoreFile).getAbsolutePath()));
			if(ecoreResource == null) System.out.println(URI.createFileURI(new File(ecoreFile).getAbsolutePath())); 
			ecoreResource.load(null);

			for (EObject o : ecoreResource.getContents()) {
				EPackage ePackage = (EPackage) o;
				EPackage.Registry.INSTANCE.put(ePackage.getNsURI(), ePackage);
			}
		}
        
        // Parsear la transformacion EGX y configurarla para producir
        // los archivos de salida en el directorio indicado
        EgxModule module = new EgxModule(new File(".").getAbsolutePath());
        module.parse(new File("picto/visorResultadosMast_test.egx"));
        
        // cargar el modelo xmi usando el metamodelo ecore
        EmfModel model = new EmfModel();
        model.setName("M");
        model.setModelFile(new File("modelos/caseva_example2.out.xmi").getAbsolutePath());
        model.setMetamodelUri("http://mast.unican.es/ecoremast/Mast2_Results");
        model.setReadOnLoad(true);
        model.setStoredOnDisposal(false);
        model.load();
        
        // Hacer el modelo disponible a la transformacion
        module.getContext().getModelRepository().addModel(model);
        
        // Ejecutar la transformacion EGX
        module.execute();

        // Disponer del modelo
        module.getContext().getModelRepository().dispose();
    }
    
    @TestFactory
    Collection<DynamicTest> comparaGraficas() throws IOException{
    	Iterator<Path> iter = Files.list(Paths.get("gen/")).iterator();
    	ArrayList<DynamicTest> tests = new ArrayList<DynamicTest>();
    	while(iter.hasNext()) {
    		String path = iter.next().toString();
    		if(path.substring(path.length()-4).equals("html")) {
    			tests.add(DynamicTest.dynamicTest(path.substring(4), () -> assertTrue(
    					Files.mismatch(Paths.get(path), Paths.get("tests/"+path.substring(4))) == -1) 
    				));
    		}
    	}
    	return tests;
    }
}