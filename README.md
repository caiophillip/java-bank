# Sistema Simples de Banco e Investimentos

## 📖 Introdução
Este projeto é um **simulador de sistema bancário** desenvolvido em Java.  
Ele permite que usuários:
- Criem contas com múltiplas chaves PIX  
- Realizem depósitos, saques e transferências  
- Criem e gerenciem produtos de investimento  
- Vinculem carteiras de investimento às contas  
- Consultem o histórico de transações  
- Atualizem rendimentos de investimentos ao longo do tempo  

A aplicação roda no console e apresenta um menu interativo para todas as operações.

---

## 📑 Índice
1. [Uso](#-uso)  
2. [Funcionalidades](#-funcionalidades)  
3. [Dependências](#-dependências)  
4. [Configuração](#-configuração)  
5. [Exemplos](#-exemplos)  
6. [Solução de Problemas](#-solução-de-problemas)  

---

## 🚀 Uso
Ao executar o programa, o menu abaixo será exibido:
```
Bem vindo ao banco!
Escolha uma opção:
1 - Criar conta
2 - Criar um investimento
3 - Criar uma carteira de investimento
4 - Depositar em uma conta
5 - Sacar de uma conta
6 - Transferir entre contas
7 - Investir
8 - Resgatar investimento
9 - Listar contas
10 - Listar investimentos
11 - Listar carteiras de investimento
12 - Atualizar investimentos
13 - Historico de conta
14 - Sair
```

Basta digitar o número correspondente à operação desejada.

---

## ✨ Funcionalidades
- **Gestão de Contas**
  - Criar contas com múltiplas chaves PIX  
  - Depositar, sacar e transferir dinheiro entre contas  
  - Consultar histórico de transações  

- **Investimentos**
  - Criar produtos de investimento com taxa de rendimento  
  - Vincular carteiras de investimento a contas  
  - Realizar depósitos e resgates em carteiras de investimento  
  - Atualizar automaticamente os rendimentos das carteiras  

- **Tratamento de Erros**
  - Previne duplicidade de chaves PIX  
  - Verifica saldo suficiente antes de operações  
  - Trata exceções para contas, carteiras ou investimentos inexistentes  

---

## 📦 Dependências
- **Java 17+** (recomendado, para compatibilidade com a sintaxe usada)  
- **Lombok**

---

## 🔧 Configuração
Não é necessário configurar nada além do ambiente Java.  
Todos os dados (contas, transações, investimentos) são armazenados **em memória** durante a execução.  
Ao encerrar o programa, tudo é resetado.

---

## 💡 Exemplos
1. **Criar uma nova conta**  
   - Digite as chaves PIX separadas por `;` (ex.: `email@teste.com;cpf123`)  
   - Informe o saldo inicial  

2. **Depositar dinheiro**  
   - Informe a chave PIX da conta  
   - Digite o valor do depósito  

3. **Realizar um investimento**  
   - Crie um produto de investimento (taxa e valor inicial)  
   - Escolha a conta vinculada  
   - Deposite valores na carteira de investimento  

4. **Resgatar investimento**  
   - Informe a chave PIX vinculada à carteira  
   - Digite o valor do resgate  
   - O valor será transferido de volta para a conta  

---

## 🛠️ Solução de Problemas
- **"Conta não encontrada"** → A chave PIX informada não existe em nenhuma conta.  
- **"Saldo insuficiente para a transação"** → O saldo da conta ou carteira não cobre a operação.  
- **"Chave pix ja em uso"** → Você tentou reutilizar uma chave PIX de outra conta.  
- **"Carteira não encontrada"** → A conta informada não possui uma carteira de investimento ativa.  
