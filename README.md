# Simple Java Calculator

A basic calculator application built with Java Swing and Gradle, featuring a clean GUI for performing arithmetic operations.

## Features

- **Basic Arithmetic Operations**: Addition (+), Subtraction (-), Multiplication (×), Division (÷)
- **User-Friendly Interface**: Clean Swing GUI with color-coded buttons
- **Additional Functions**:
  - Clear All (C) - Resets the calculator completely
  - Clear Entry (CE) - Clears the current entry only
  - Backspace (⌫) - Removes the last entered digit
  - Toggle Sign (±) - Changes between positive and negative numbers
  - Decimal Point Support - For floating-point calculations
- **Error Handling**: Graceful handling of division by zero and other errors

## Project Structure

```
calculator/
├── build.gradle
├── gradle/
│   └── wrapper/
│       └── gradle-wrapper.properties
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── example/
│   │               └── calculator/
│   │                   └── Calculator.java
│   └── test/
│       └── java/
│           └── com/
│               └── example/
│                   └── calculator/
│                       └── CalculatorTest.java
└── README.md
```

## Requirements

- Java 11 or higher
- Gradle (included via Gradle Wrapper)

## How to Run

### Using Gradle Wrapper (Recommended)

1. **Clone or download the project**
2. **Navigate to the project directory**
3. **Run the calculator:**
   ```bash
   # On Windows
   gradlew run
   
   # On macOS/Linux
   ./gradlew run
   ```

### Alternative: Using the custom task

```bash
# On Windows
gradlew runCalculator

# On macOS/Linux
./gradlew runCalculator
```

## Building the Project

### Compile the project:
```bash
./gradlew build
```

### Run tests:
```bash
./gradlew test
```

### Create a JAR file:
```bash
./gradlew jar
```
The JAR file will be created in `build/libs/calculator-1.0.0.jar`

### Run the JAR file:
```bash
java -jar build/libs/calculator-1.0.0.jar
```

## Usage

1. **Launch the application** - A calculator window will appear
2. **Enter numbers** - Click the number buttons (0-9)
3. **Perform operations** - Click an operator button (+, -, ×, ÷)
4. **Get results** - Click the equals button (=)
5. **Use additional features**:
   - **C**: Clear everything and start over
   - **CE**: Clear the current number being entered
   - **⌫**: Remove the last digit entered
   - **±**: Toggle between positive and negative
   - **.**: Add a decimal point

## Example Calculations

- **Simple Addition**: `5 + 3 = 8`
- **Decimal Operations**: `2.5 × 4 = 10`
- **Negative Numbers**: `±5 + 10 = 5`
- **Division**: `15 ÷ 3 = 5`

## Development

### Code Structure

- **Calculator.java**: Main class containing both the GUI and calculation logic
- **CalculatorTest.java**: Unit tests for the calculation functionality
- **build.gradle**: Gradle build configuration

### Future Enhancements

- Extract calculation logic into a separate `CalculatorEngine` class
- Add memory functions (M+, M-, MR, MC)
- Implement keyboard support
- Add more advanced operations (square root, percentage, etc.)
- Improve GUI responsiveness and appearance

## Testing

The project includes basic unit tests for the calculation logic. Run tests with:

```bash
./gradlew test
```

Test results will be available in `build/reports/tests/test/index.html`

## License

This project is created for educational purposes and is free to use and modify.