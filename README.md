
# Shopping Chat Bot

This project implements a chatbot designed to assist with daily activities, integrated with Telegram.
## Features

- **Multipurpose Chatbot**: Assists with various tasks and activities.
- **Telegram Integration**: Seamless connection with Telegram for user interaction.
- **Customizable**: Easy to configure and extend for specific use cases.

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- Gradle
- MySQL Database
- Telegram Bot API token

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/ZeleZ-T/ChatBot.git
   cd ChatBot
   ```

2. Configure your environment:
    - Rename `.env.example` to `.env` and set the required variables, including `TELEGRAM_API_TOKEN` and MySQL database credentials (`DATABASE_URL`, `DATABASE_USER`, `DATABASE_PASSWORD`).

3. Build the project:
   ```bash
   ./gradlew build
   ```

### Configuring MySQL Database


1. Configure your database:
   - Rename `init.sql.example` to `init.sql` and set the required database credentials (`user`, `password`).
   - Run the setup script:
     ```bash
     mysql -u your_username -p < migrations/init.sql
     ```
2. Update the `.env` file with the database connection details:
   ```
   DATABASE_URL=localhost:XXXX/EXAMPLE
   DATABASE_USER=user_example
   DATABASE_PASSWORD=pass_example
   ```
3. Build the bot.
### Running the Bot

Start the bot with:
```bash
./gradlew run
```

### Configuring Telegram Bot

1. Create a bot on Telegram via [@BotFather](https://t.me/BotFather) and obtain an API token.
2. Update the `.env` file with your `TELEGRAM_API_TOKEN`.

## Contributing

1. Fork the repository.
2. Create your feature branch (`git checkout -b feature/AmazingFeature`).
3. Commit your changes (`git commit -m 'feat: add some amazingFeature'`).
   - Please ensure your commit messages follow the [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/) standard.
4. Push to the branch (`git push origin feature/AmazingFeature`).
5. Open a pull request.

## License

This project is licensed under the Apache License 2.0.