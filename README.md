# BaseballEliminationProblem

- Pré-requitos
    -- Java 8 (preferencialmente da Oracle)

- Hierarquia do Projeto
    -- /bin
        --- Contém os executáveis do projeto;
        --- Tópico logo abaixo tem instruções pra rodar o programa
    -- /bin/tests
        --- Casos de teste do algoritmo
    -- /src
        --- Contém os códigos do software
        --- Contém dois projetos, o BaseballElimination contém somente a lógica do algoritmo, já o BaseballEliminationGrafico é o que chama a lógica e exibe a interface gráfica do projeto;
    

- Execução
Para executar o software basta rodar o arquivo .jar localizado na pasta bin do projeto. Executando o .jar ele irá pegar automaticamente os casos de teste presentes em bin/testes. Caso queira um novo caso de teste basta inserir um .txt com o mesmo padrão dos presentes lá.
    -- Caso não rode
    Caso o software não rode é devido falta de permissões para execução, nesse caso basta colocar permissões de execução para o arquivo da seguinte forma (assumindo que o terminal tá na pasta bin):
        --- chmod +x BaseballEliminationGrafico.jar

- IDE
A IDE utilizada foi a Intellij da JetBrains portando não será possível importar o projeto diretamente no Eclipse, caso seja necessário basta criar um projeto novo no Eclipse ou Netbeans e copiar a pasta src pra dentro dele.
