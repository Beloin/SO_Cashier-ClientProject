# Problema 01

Uma agência bancária tem N caixas. Cada cliente que entra na agência recebe uma senha e espera (bloqueado) até que sua
senha seja chamada. Após ser atendido, o cliente deve sair da agência (o thread deve ser finalizado). Sempre que um
caixa se tornar desocupado, o cliente que possui a próxima senha é chamado. Caso o caixa fique desocupado e não exista
nenhum cliente para ser atendido, ele deve dormir (bloqueado) até que chegue um novo cliente. Utilizando semáforos,
modele esse problema evitando todas as condições de corrida que possam acontecer.

## Entradas:

Ao iniciar a execução, a aplicação deverá solicitar ao usuário a quantidade de caixas da agência (N).

## Thread Caixa

Ao iniciar a execução da aplicação, todos os N caixas deverão ser criados. Durante a criação de cada thread caixa deverá
ser definido automaticamente o seu Id (identificador), que pode ser um nome ou um número.

## Thread Cliente

A aplicação deve possuir um botão para que o usuário possa criar um cliente a qualquer momento. Durante a criação de
cada thread cliente, os seguintes parâmetros deverão ser definidos:
• Id = identificador do cliente (número ou nome).
• Ta = tempo de atendimento (tempo que o cliente fica sendo atendido por um caixa). A interface deverá mostrar que o
cliente e o caixa estão executando durante todo este tempo.
• Senha

## Saídas:

A interface deverá atender aos seguintes requisitos:
• Mostrar os dados de cada caixa:
▪ Identificador;
▪ Status (atendendo o cliente Y ou dormindo).

    • Mostrar os dados de cada cliente:
        ▪ Identificador;
        ▪ Tempo de atendimento;
        ▪ Status (dormindo na fila, em atendimento pelo caixa X ou já foi atendido).

    • Mostrar um log com os principais eventos de cad processo.
