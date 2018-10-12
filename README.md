## MiniC#
# Fase #1 - Analizador Léxico
> 1. En primer lugar el programa inicia pidiendo la dirección en donde se encuentra el archivo que debe analizar.
Luego se copia a otro archivo .txt desde donde el programa va a ejecutar el Lexer
Una vez ejecutado el lexer se procede a evaluar cada uno de los tokens que se especificaron en él guardando los resultados en otra carpeta en la misma dirección en donde se encotraba el archivo original. 
>2. Funciona correctamente pues se validaron todos los posibles errores en nuevas expresiones regulares, las cuales capturaran cualquier error. 

# Fase #2 - Analizador Sintáctico 
> Los errores se manejan partiendo del analizador léxico. Él si detecta un caracter no válido en su análisis manda a imprimir y detiene la ejecución. Si el análisis léxico no contiene errores pasa directamente al análisis sintáctico. Con ayuda de los prarámetros que proporciona JFlex es posible conocer la línea, la columna y el símbolo. En el momento en que una expresión no coinside con los tokens que **_Mini C#_** maneja, entonces llamará a la función ```syntax_error(token)``` el cual lleva como parámetro el Token actual. Ese error es almacenado en una lista _ArrayList_ para al momento de haber terminado el análisis imprimirlo en pantalla. 
