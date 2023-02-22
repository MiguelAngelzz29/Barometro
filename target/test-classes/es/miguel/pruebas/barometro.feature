#language: es
Requisito: Medir la presión para determinar el tiempo

Escenario: Calcular tiempo meteorológico
Cuando no hay suficientes mediciones
Entonces advertencia

Escenario: Determinar día soleado
Cuando ultima presion es mayor que presion Referencia
Entonces sol

Escenario: Determinar día de tormenta
Cuando ultima presion menor que penultima presion menos uno
Entonces tormenta

Escenario: Determinar día con intervalos nubosos
Cuando ultima presion es mayor que penultima presion mas uno
Entonces nubes

Escenario: Deternminar si la presión sube lentamente durante 24h
Cuando sube la presion en 24h
Entonces sol 24h





