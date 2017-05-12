import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

public class Application {

	public static void main(String[] args) {
		
		File file = new File("log.txt"); //Variável que representa o arquivo de entrada
		BufferedReader reader = null; //Variável para fazer a leitura do arquivo
		FileWriter arq, arq2; //Variáveis para a criação de arquivos de saída contendo o resultado final
		PrintWriter print, print2; //Variáveis para a escrita nos arquivos de saída contendo o resultado final
		Vector readCharRq; //Vetor para receber os carateres referentes à URL do request_to
		Vector readCharSt; //Vetor para receber os carateres referentes ao code do response_status
		Request reqTo; //Objeto referente ao request_to (request_to e cont)
		Status status; //Objeto referente ao response_status (status e cont)
		Vector<Request> vecReqTo; //Vetor para objetos do tipo Request
		Vector<Status> vecStatus; //Vetor para objetos do tipo Status
		Request sorted; //Objeto para auxiliar na ordenação do vetor to tipo request
		boolean boolrq, findrq, boolst, findst; //Variáveis booleanas para trabalhar tanto com objetos Request como Status
		String text; //Variável com o conteúdo lido do arquivo de entrada
				
		try {
			
			//Inicializando variáveis
		    reader = new BufferedReader(new FileReader(file));
		    arq = new FileWriter("saida1.txt");
		    arq2 = new FileWriter("saida2.txt");
		    print = new PrintWriter(arq);
		    print2 = new PrintWriter(arq2);
		    readCharRq = new Vector();
		    readCharSt = new Vector();
		    vecReqTo = new Vector<>();
		    vecStatus = new Vector<>();
		    text = null;
		    boolrq = false;
		    boolst = false;
		    
		    
		    //Comando de repetição para ler o aquivo de entrada. Realizará a leitura enquanto não chegar ao final do mesmo
		    while ((text = reader.readLine()) != null) {
		    	//Comando de repetição para percorer a linha lida do arquivo de entrada
		    	for(int i = 0; i < text.length(); i++){
		    		//boolrq é setado true quando encontramos a String request_to=" na linha do arquivo analisado
		    		//Verifica se essa String já foi encontrada e se o próximo caractere lido é diferente do caractere:  " .
		    		if (boolrq == true && text.charAt(i) != '\"' ){
		    			//Se verdade percorre a linha até encontrar o próximo caractere: ".
		    			while (text.charAt(i) != '\"'){
		    				//Guarda no vetor (Request) de caracteres o elemento lido
		    				readCharRq.add(text.charAt(i));
		    				//incrementa a posição de i (servirá tanto no while como no for para evitar a releitura)
		    				i++;
		    			}
		    			//Seta a variável boolrq como false para que ela auxilie a encontrar a próxima String request_to="
		    			boolrq = false;
		    		}
		    		//boolst é setado true quando encontramos a String response_status=" na linha do arquivo analisado
		    		//Verifica se essa String já foi encontrada e se o próximo caractere lido é diferente do caractere:  " .
		    		if (boolst == true){
		    			//Se verdade percorre a linha até encontrar o próximo caractere: ".
		    			while (text.charAt(i) != '\"'){
		    				//Guarda no vetor (Status) de caracteres o elemento lido
		    				readCharSt.add(text.charAt(i));
		    				//incrementa a posição de i (servirá tanto no while como no for para evitar a releitura)
		    				i++;
		    			}
		    			//Seta a variável boolst como false para que ela auxilie a encontrar a próxima String response_status="
		    			boolst = false;
		    		}
		    		
		    		//verifica se o restante da linha que será analisada é menor do que a palavra response_status="
		    		if(text.length() - i < 17)
		    			//caso seja verdade, não precisa percorre mais o arquivo e por isso mudamos o valor de i para encerrar o comando de repetição for.
		    			i = text.length();
		    		else{
		    			//caso contrário, verifica a substring da posição i até i+12 é igual a String request_to="
		    			if (text.substring(i, i+12).equals("request_to=\"")){
		    				//incrementa a posição de i para o próxima caractere após a String request_to="
		    				i = i+11;
		    				//seta boolrq como true para dizer que a String request_to=" foi encontrada
		    				boolrq = true;
		    			}
		    			//verifica se a substring da posição i até i+17 é igual a String response_status="
		    			else if(text.substring(i, i+17).equals("response_status=\"")){
		    					//incrementa a posição de i para o próxima caractere após a String response_status="
			    				i = i+16;
			    				//seta boolst como true para dizer que a String response_status=" foi encontrada
			    				boolst = true;
		    				}
		    		}
		    	}
		    	
		    	//Inicializa um Objeto do tipo Request
		    	reqTo = new Request();
		    	reqTo.request_to = "";
		    	reqTo.cont = 1;
		    	
		    	//Inicializa um Objeto do tipo Status
		    	status = new Status();
		    	status.status = "";
		    	status.cont = 1; 
		    	
		    	//Percorre o vetor com os caracteres do request_to e concatena o resultado na variável reqTo.request_to do objeto reqTo
				for (int j = 0; j<readCharRq.size(); j++){
					//Concatena caracteres formando a String final.
					reqTo.request_to = reqTo.request_to + readCharRq.get(j);
		    	}
				
				//Percorre o vetor com os caracteres do response_status e concatena o resultado na variável status.status do objeto status
				for (int j = 0; j<readCharSt.size(); j++){
					//Concatena caracteres formando a String final.
					status.status = status.status + readCharSt.get(j);
		    	}
				
				//Limpa os vetores para receber os próximos resultados lidos. 
				readCharRq.removeAllElements();
				readCharSt.removeAllElements();
		    	
				//verifica se a variável request_to do objeto reqTo está vazia
		    	if(reqTo.request_to != ""){
		    		//caso verdade, verifica se o vetor final está vazio
		    		if(vecReqTo.size() == 0){
		    			//caso verdade, insere o objeto na primeira posição do vetor final
		    			vecReqTo.add(reqTo);
		    		}
		    		//Caso o vetor não esteja vazio, será necessário verificar se já existe esse objeto dentro do mesmo
			    	else{
			    		//Inicializa o Booleano findrq como false. Essa variável informa se um objeto semelhante foi encontrado dentro do vetor final
			    		findrq = false;
			    		//Percorre o vetor final para verificar se existe igualdade com o objeto a ser inserido no vetor final
			    		for(int j = 0; j < vecReqTo.size(); j++){
			    			//verifica se existe igualdade com o objeto a ser inserido no vetor final
			    			if(reqTo.request_to.equals(vecReqTo.get(j).request_to)){
			    				//caso exista, altera a variável cont do objeto analisado dentro do vetor final
			    				vecReqTo.get(j).cont++;
			    				//altera o valor de j para encerrar o comando de repetição for
			    				j = vecReqTo.size();
			    				//seta a variável booleana como true, para informar que o objeto foi encontrado no vetor final
			    				findrq = true;
			    			}
			    		}
			    		//verifica se o objeto analisado já foi inserido no vetor final
			    		if(findrq != true){
			    			//adiciona o novo objeto no vetor final
			    			vecReqTo.add(reqTo);
			    			//seta a variável booleana como false para analisar um possível novo objeto
		    				findrq = false;
			    		}
			       }
		    	}
		    	
		    	//verifica se a variável response_status do objeto status está vazia
		    	if(status.status != ""){
		    		//caso verdade, verifica se o vetor final está vazio
		    		if(vecStatus.size() == 0){
		    			//caso verdade, insere o objeto na primeira posição do vetor final
		    			vecStatus.add(status);
		    		}
		    		//Caso o vetor não esteja vazio, será necessário verificar se já existe esse objeto dentro do mesmo
			    	else{
			    		//Inicializa o Booleano findst como false. Essa variável informa se um objeto semelhante foi encontrado dentro do vetor final
			    		findst = false;
			    		//Percorre o vetor final para verificar se existe igualdade com o objeto a ser inserido no vetor final
			    		for(int j = 0; j < vecStatus.size(); j++){
			    			//verifica se existe igualdade com o objeto a ser inserido no vetor final
			    			if(status.status.equals(vecStatus.get(j).status)){
			    				//caso exista, altera a variável cont do objeto analisado dentro do vetor final
			    				vecStatus.get(j).cont++;
			    				//altera o valor de j para encerrar o comando de repetição for
			    				j = vecStatus.size();
			    				//seta a variável booleana como true, para informar que o objeto foi encontrado no vetor final
			    				findst = true;
			    			}
			    		}
			    		//verifica se o objeto analisado já foi inserido no vetor final
			    		if(findst != true){
			    			//adiciona o novo objeto no vetor final
			    			vecStatus.add(status);
			    			//seta a variável booleana como false para analisar um possível novo objeto
		    				findst = false;
			    		}
			       }
		    	}
		    }
		    
		    //Ordenação do Vetor final de objetos Request
		    if (vecReqTo.size() != 0)
			    for (int i = 0; i < vecReqTo.size(); i++){
			    		for(int j = i+1; j < vecReqTo.size(); j++){
			    			if(vecReqTo.get(j).cont < vecReqTo.get(i).cont){
			    				sorted = new Request();
			    				sorted.request_to = vecReqTo.get(j).request_to;
			    				sorted.cont = vecReqTo.get(j).cont;
			    				vecReqTo.get(j).cont = vecReqTo.get(i).cont;
			    				vecReqTo.get(j).request_to = vecReqTo.get(i).request_to;
			    				vecReqTo.get(i).cont = sorted.cont;
			    				vecReqTo.get(i).request_to = sorted.request_to;
			    			}
			    		}
			    }
		    
		    //Impressão de resultados da primeira questão. Tanto em um arquivo TXT, que será criado na pasta do projeto, como no console de onde estiver executando o código. 
		    print.println("Primeira saída do teste da Moip: \n");
		    System.out.println("Primeira saída do teste da Moip: \n");
		    
		    for(int i = vecReqTo.size(); i>vecReqTo.size()-3; i--){
		    	print.println(vecReqTo.get(i-1).request_to + " - " + vecReqTo.get(i-1).cont);
		    	System.out.println(vecReqTo.get(i-1).request_to + " - " + vecReqTo.get(i-1).cont);
		    }
		    
		  //Impressão de resultados da segunda questão. Tanto em um arquivo TXT, que será criado na pasta do projeto, como no console de onde estiver executando o código.
		    print2.println("Segunda saída do teste da Moip: \n");
		    System.out.println("\nSegunda saída do teste da Moip: \n");
		    
		    for(int i = 0; i < vecStatus.size(); i++){
		    	print2.println(vecStatus.get(i).status + " - " + vecStatus.get(i).cont);
		    	System.out.println(vecStatus.get(i).status + " - " + vecStatus.get(i).cont);
		    }
		    
		    //fecha os arquivos de saída
		    arq.close();
		    arq2.close();
		    
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		} finally {
		    try {
		        if (reader != null) {
		            reader.close();
		        }
		    } catch (IOException e) {
		    }
		}

	}

}
