# Relato sobre testes

Os testes foram realizados com diferentes tamanhos de vetor para verificar a ordenação correta e a integração entre as threads.
Além disso, foram testados os tratamentos de exceção, inserindo valores não numéricos, valores negativos e caracteres estrangeiros.
Durante os primeiros, algumas vulnerabilidades foram encontradas em partes mais difíceis de acessar no desenvolvimento(como o input manual, já que muitas vezes eram testados vetores com tamanhos variáveis, geralmente entre 1.000 e 5.000.000).
Foi observado que, com 4 processadores, o número de rodadas frequentemente chega em 2, sendo até 1 caso o número seja muito pequeno.
