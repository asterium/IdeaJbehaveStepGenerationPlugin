## About this plugin
This plugin is intended to simplify usage of Jbehave framework in Intellij IDEA.
Idea of this plugin was borrowed from Visual Studio + Specflow integration tool, that allows to produce step definitions in one-click way.

### How-To for clipboard way to use plugin:
1. Place caret on step in *.story file you want to generate
2. Press Alt+Enter
3. Select "Generate BDD Step"
4. Go to file you want your step definition to be placed into and paste ready step definition from standard clipboard (Ctrl+V)
5. Enjoy:)

### How-To for class-insertion way to use plugin
1. Place caret on step in *.story file you want to generate
2. Press Alt+Enter
3. Select "Generate BDD Step into class"
4. Pick proper class for newly generated step definition
5. Close class picker by clicking "Select" (Enter) for staying in current feature file  
    or alternatively  
    Close class picker by clicking "Select and navigate" (Alt+Enter) to open your selected class on a position where new method is inserted
6. Enjoy :)


### Note:
This plugin supports automatic parameter extraction. Parameters use the mechanism of Jbehave parameter injection [Jbehave docs](http://jbehave.org/reference/latest/parameter-injection.html).
So far here is the list of parameters supported:
1. String - parameters for parametrized scenarios (in <> brackets)
2. Double
3. Integer
4. Boolean
5. ExamplesTable

### Shortcuts:
This plugin allows enables new shortcuts to speed up step generation:
1. Click Ctrl+Alt+B in feature file to generate step for the line under caret
2. On classpicker dialog use Alt+Enter to invoke "Select and navigate" action


### Further improvements:
1. ~~Make step definitions insertable into class files~~
2. Generate all step definitions in the story file
3. Integrate this functionality with IntelliJbehave plugin
4. Improve parameter type support
5. Implement rearrangement of method inside step definition class