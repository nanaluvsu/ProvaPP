# Sobre a prova

## Grupo: 08
### RAs:
- 24011150
- 25007828
- 23008057

## Como executar

rodar com ```bash java -Xmx8g MaiorVetorAproximado.java```

## Proposta

Ordenar um grande vetor de números inteiros do tipo **byte**. Para tanto, você deve implementar um sistema distribuído de ordenação em Java, no qual um programa D (Distribuidor) gera um grande vetor de números inteiros aleatórios do tipo **byte**, particiona o vetor em partes de tamanho aproximadamente iguais, e envia essas partes a diferentes linhas de execução realmente paralelas (devem existir em número exatamente igual à **quantidade de processadores menos um**) que executam a ordenação em paralela; são as threads ordenadoras.

Assim, várias threads devem ser postas em execução para realizar simultaneamente partes da ordenação designada pela main; é claro que para ter simultaneidade real é preciso por em execução uma quantidade de threads no máximo igual à quantidade de processadores que há na máquina.

Em suma, cada thread ordenadora ordena a parte que lhe cabe recursivamente pelo método Merge Sort, os resultados de todas as threads serão juntadas 2 a 2 num vetor só por threads juntadoras (que fazem merge) até obter um só vetor. Várias rodadas de execução e threads juntadoras devem ser necessárias, cada rodada diminuindo pela metade a quantidade necessária de juntadoras.

## Boas práticas exigidas e outras obrigações

- Ofereça ao usuário a possibilidade de decidir quantos elementos quer ter no vetor a ser ordenado, bem como de preenchè-lo à mão ou de forma automática com números aleatórios.
- Ofereça ao usuário, ao final do processo, a opção de decidir printar todo o vetor ordenado ou a parte que decidir printar dele.
- Capture e trate exceções adequadamente.
- Use ``` join()```  para aguardar a finalização das threads.
- Insira mensagens de log informativas em ambos os programas.
- Faça também um programa que realize a ordenação sem paralelismo.
- Meça os tempos de execução de ambos os programas para fins de comparação.
