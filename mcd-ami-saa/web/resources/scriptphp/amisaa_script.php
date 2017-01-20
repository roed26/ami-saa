
<?php
/***
este Script contiene la logica necesariara para revisar la carpeta donde llegan los
archivos enviados por cada maestro, y si hay archivos notificar al servidor;
*/

$ruta_carpeta = '/home/usuarioftp/amisaaftp/ftp'; //directorio ftp donde llegan los archivos
$url = 'http://localhost:8080/mcd-ami-saa/webresources/webservice'; //url del servicio rest del servidor
/***
esta Funcion que revisa que la carpeta ftp esta vacia si no esta
vacia envia una señal a un servicio web en java notificando que hay arachivos
nuevos en la carpeta
***/
function scanFolder()
{
	global $ruta_carpeta;

	$carpeta = @scandir($ruta_carpeta);
 
	if (count($carpeta) > 2){//si una carpeta esta vacia el contador  retorna el numero 2
		return TRUE;
	}else
	{
		return FALSE;
	}

}
/***
este metodo es usado para notificar al servidor directamente al servidor por medio de un servicio rest
*/
function serverNotify()
{
   global $url;
   $ch = curl_init();
   curl_setopt($ch,CURLOPT_URL,$url);
   curl_setopt($ch,CURLOPT_RETURNTRANSFER,true);
   echo curl_exec($ch);   
   curl_close($ch);
}

/**metodo que comprueba si una carpeta contiene archivos mediante la funcion sancafolder y si esta arroja True
notifica al servidor por medio de la funcion serverNotify
*/
function run()
{
	if(scanFolder())//si la funcion retorna false no hacemos nada
	{
		serverNotify();//solo notificamos cuando hay archivos dentro de la carpetaftp
	}
}

run();

?>
