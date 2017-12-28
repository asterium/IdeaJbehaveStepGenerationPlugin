## About this plugin
This plugin is intended to simplify usage of Jbehave framework in Intellij IDEA.
Idea of this plugin was borrowed from Visual Studio + Specflow integration tool, that allows to produce step definitions in one-click way.
Currently, this plugin allows to generate step definition and insert it into clipboard.

### This plugin how-to:
1. Place caret on step in *.story file you want to generate
2. Press Alt+Enter
3. Select "Generate BDD Step"
4. Go to file you want your step definition to be placed into and paste ready step definition from standard clipboard (Ctrl+V)
5. Enjoy:)


### Note:
This plugin supports automatic parameter extraction. Parameters use the mechanism of Jbehave parameter injection [Jbehave docs](http://jbehave.org/reference/latest/parameter-injection.html).
So far here is the list of parameters supported:
1. String - parameters for parametrized scenarios (in <> brackets)
2. Double
3. Integer ( confusion between int and long was not tested)


### Further improvements:
1. Make step definitions insertable into class files
2. Generate all step definitions in the story file
3. Integrate this functionality with IntelliJbehave plugin
4. Improve parameter type support