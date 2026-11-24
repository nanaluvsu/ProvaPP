# Sobre a prova

## Grupo: 02

### RAs

- 24011150
- 25007828
- 23008057

## Como executar

rodar com ```java -Xmx8g MaiorVetorAproximado.java```

***Nota:*** instrução de execução anterior não compila todos os arquivos, logo, é necessário rodar com:

```javac *.java; java -Xmx8G MaiorVetorAproximado```

## Proposta

Ordenar um grande vetor de números inteiros do tipo **byte**. Para tanto, você deve implementar um sistema distribuído de ordenação em Java, no qual um programa D (Distribuidor) gera um grande vetor de números inteiros aleatórios do tipo **byte**, particiona o vetor em partes de tamanho aproximadamente iguais, e envia essas partes a diferentes linhas de execução realmente paralelas (devem existir em número exatamente igual à **quantidade de processadores menos um**) que executam a ordenação em paralela; são as threads ordenadoras.

Assim, várias threads devem ser postas em execução para realizar simultaneamente partes da ordenação designada pela main; é claro que para ter simultaneidade real é preciso por em execução uma quantidade de threads no máximo igual à quantidade de processadores que há na máquina.

Em suma, cada thread ordenadora ordena a parte que lhe cabe recursivamente pelo método Merge Sort, os resultados de todas as threads serão juntadas 2 a 2 num vetor só por threads juntadoras (que fazem merge) até obter um só vetor. Várias rodadas de execução e threads juntadoras devem ser necessárias, cada rodada diminuindo pela metade a quantidade necessária de juntadoras.

## Boas práticas exigidas e outras obrigações

- Ofereça ao usuário a possibilidade de decidir quantos elementos quer ter no vetor a ser ordenado, bem como de preenchè-lo à mão ou de forma automática com números aleatórios.
  
> Feito

- Ofereça ao usuário, ao final do processo, a opção de decidir printar todo o vetor ordenado ou a parte que decidir printar dele.
  
> Feito

- Capture e trate exceções adequadamente.

> Feito

- Use ```join()```  para aguardar a finalização das threads.

> Feito

- Insira mensagens de log informativas em ambos os programas.

> Feito

- Faça também um programa que realize a ordenação sem paralelismo.
  
> Feito

- Meça os tempos de execução de ambos os programas para fins de comparação.

> Feito

---

## 2ª parte
### Fluxo (apenas para organização mental)
1. Cliente: Solicita o tamanho do vetor e a população de forma manual ou automática.
2. Resto de Distribuidor vira um servidor em execução em várias máquinas, recebendo de Cliente um segmento do vetor mencionado em **1.** e o ordenando PARALELAMENTE, como na parte 1.
3. A main do servidor **deve iniciar a thread AceitadoraDeConexao para então esperar a digitação do comando desativar** que, se for digitado, encerrará a conexão do servidor.
4. A thread AceitadoraDeConexao **deverá iniciar a thread SupervisoraDeConexao e então esperar seu encerramento**, usando o método ```join```.
5. A thread SupervisoraDeConexao deverá atender às mensagens(**herdando da classe Comunicado**) solicitando **ordenação ou merge**, além de **resultado de ordenação e resultado de merge**, que serão respondidas com mensagens indicando **ordenação pronta, merge pronto e o resultado em si de uma ordenação ou merge, classe que deverá conter um vetor(vetor resultante).**
6. **ESSE SERVIDOR DEVERÁ ATENDER APENAS A UM CLIENTE em um dado momento**(por isso o uso do ```join```), embora possa atender em sequência outro Cliente que eventualmente rode e solicite conexão após o Cliente anterior haver mandado mensagem informando que está encerrando o uso do servidor.

### Como executar?

```javac *.java; java -Xmx8G Cliente```