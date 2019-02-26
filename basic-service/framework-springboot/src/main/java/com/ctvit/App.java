package com.ctvit;

import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.ctvit")
public class App {
	
	private static final Logger LOGGER = getLogger(App.class);
	public static void main(String[] args) {
		// 程序启动入口
        SpringApplication.run(App.class,args);
	}
	

    /*public static void main(String args[]){
        Configuration config = DefaultConfigurer.defaultConfiguration()
                .configureAggregate(BankAccount.class)
                .configureEmbeddedEventStore(c -> new InMemoryEventStorageEngine())
                .buildConfiguration();
        config.start();
        AccountId id = new AccountId();
        config.commandGateway().send(new CreateAccountCommand(id, "MyAccount",1000));
        config.commandGateway().send(new WithdrawMoneyCommand(id, 500));
        config.commandGateway().send(new WithdrawMoneyCommand(id, 500));
        config.commandBus().dispatch(asCommandMessage(new CreateAccountCommand(id, "MyAccount", 1000)));
        config.commandBus().dispatch(asCommandMessage(new WithdrawMoneyCommand(id, 500)));
    }*/

}
