
import java.io.File;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.epsilon.egl.EgxModule;
import org.eclipse.epsilon.emc.emf.EmfModel;

public class Tests {
    
    public static void main(String[] args) throws Exception {
    	
    	// Registrar los parsers xmi y Ecore con EMF
        Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
        Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());
        
        // Parsear la transformacion EGX y configurarla para producir
        // los archivos de salida en el directorio indicado
        EgxModule module = new EgxModule(new File(".").getAbsolutePath());
        module.parse(new File("tests/visorResultadosMast_test.egx"));
        
        // cargar el modelo xmi usando el metamodelo ecore
        EmfModel model = new EmfModel();
        model.setName("M");
        model.setModelFile(new File("modelos/caseva_example2.out.xmi").getAbsolutePath());
        model.setMetamodelFile(new File("metamodelos/Mast2_Results.ecore").getAbsolutePath());
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
}