package weka.classifiers.trees;

import weka.classifiers.AbstractClassifier;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.classifiers.trees.Id3;

import java.util.ArrayList;


/**
 * @author alumno
 *         Se debe comentar el código
 */
public class Id3 extends AbstractClassifier {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1789045553777829235L;

	// CLASE POR OMISIÓN
	int cl = 0;

	/*
	 * (non-Javadoc)
	 * 
	 * @see weka.classifiers.Classifier#buildClassifier(weka.core.Instances)
	 */
	/**
	 * @param datos
	 */
	@Override
	public void buildClassifier(Instances datos) throws Exception {
		// TODO Crea un arbol
		cl = 0;

		calcEntropia(datos);
	}

	/**
	 * @param ejemplo
	 */
	@Override
	public double classifyInstance(Instance ejemplo) throws Exception {
		// TODO Dado un arbol clasifica un ejemplo
		return super.classifyInstance(ejemplo);
	}

	/**
	 * @param NumValues
	 *                  Lista con el numero de distintos valores encontrados
	 */
	public double entropia(ArrayList<Integer> NumValues) {
		int tamLista = NumValues.size();
        int i = 0;
        double total = 0;
        double e = 0; 
        double n = 0;
        double elem =0;

        for(i = 0; i < tamLista; i++){
            elem = (int) NumValues.get(i);
            total = total + elem;
        }

        if(NumValues.contains(0)){
            return 0;
        }else{
            for(i = 0; i < tamLista; i++){
                elem = NumValues.get(i);
                n = elem / total; 
                e = e  - (elem/total * log2(n));
            }
        }
        
        return e;
	}

	public double log2 (double n){
		return Math.log(n)/Math.log(2);
	}

	public void calcEntropia(Instances datos){
		String dat;
		int i,j,k;
		int aux;
		Double[] ents = new Double[datos.numAttributes()];
		ArrayList<Integer> list = new ArrayList<Integer>();

		//Recuperamos datos
		for(j = 0; j < datos.numAttributes(); j++){ //Columnas
			for(k = 0; k < datos.attribute(j).numValues();k++){
				aux = 0;
				for(i = 0; i < 14; i++){ //Renglones
					if(datos.attribute(j).value(k) == datos.get(i).stringValue(j)){
						aux = aux + 1;
					}
				}
				list.add(aux);	
			}
		}

		System.out.println(list);
	}

	/**
	 * 
	 */
	@Override
	public String toString() {
		return "ID3 Nuevo";
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// Para pruebas
		Id3 id3 = new Id3();

		// Lee datos de tenis
		DataSource source = new DataSource("weather.nominal.arff");
		Instances datos = source.getDataSet();

		id3.buildClassifier(datos);

		// Imprimelos
		System.out.println(datos);
		
		// Clasifico el primer dato
		double c = id3.classifyInstance(datos.firstInstance());
		System.out.println("Ejemplo: " + datos.firstInstance());
		System.out.println("La clase fue " + c);
		
	}

}
