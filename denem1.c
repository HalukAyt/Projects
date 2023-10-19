
/*

#include<stdio.h>
#include<conio.h>

int main(){
	printf("Merhaba Dunya\n");
	getch();
	return 0;	
	}
}

*/

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/*

#include <stdio.h>
int main()
{
	int tamsayi1,tamsayi2,toplam;
	 printf("ilk Tamsayiyi Giriniz\n");
	  scanf("%d",&tamsayi1);
	 printf ("ikinci Tamsayiyi Giriniz\n");
	  scanf("%d",&tamsayi2);
	   toplam=tamsayi1+tamsayi2;
	 printf("Toplam %d dir \n",toplam);
	  getch();
 
	return 0;
 
}

*/

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/*

#include<stdio.h>
#include<conio.h>

int main(){
	int vize;
	 printf("Vize notunuzu giriniz: \n");
	  scanf("%d",&vize);
	
	if(vize > 50){
		printf("gectiniz!");
	}
	else{printf("Kaldiniz!!");	}
	}
}

*/

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/*		
		
#include<stdio.h>
#include<conio.h>
																//ÝF ELSE
int main(){

	int vize;
	 printf("Vize notunuzu giriniz: \n");
	  scanf("%d",&vize);
	
	if(vize >= 90){ printf("AA ile dersi gectiniz. "); }
	
	else if(vize >= 85){ printf("BA ile dersi gectiniz. "); }
	
	else if(vize >= 80){ printf("BB ile dersi gectiniz. "); }
	
	else if(vize >= 75){ printf("CB ile dersi gectiniz. "); }
	
	else if(vize >= 50){ printf("Kosullu dersi gectiniz. "); }
	
	else{ printf("Dersten Kaldýnýz. "); }
	
	getch();
	return 0;
	
		}
		
*/

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/*
																//SWÝTCH CASE
#include<stdio.h>
#include<conio.h>
																			//break komutunun önemini unutma!
int main(){

	int renk = 1;
	
	printf("Lutfen Renk Seciniz: (1= Kirmizi , 2= Yesil , 3= Mavi)\n ");
		scanf("%d",& renk);
	 
	switch(renk){
		case 1:  printf("Kirmizi Rengi Sectiniz... ");
			break;
		case 2:  printf("Yesil Rengi Sectiniz... ");
			break;
		case 3:  printf("Mavi Rengi Sectiniz... ");
			break;
		default: printf("Renk Secilmedi! ");
	}
	getch();
	return 0;
}

*/

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/*

#include<stdio.h>
#include<conio.h>

int main(){

	float a,b,c,d,e,f;
		a=1/3;							// float =! int/int --- int/int = int
		b=1/3.0;						// float = int/float
		c=1.0/3;						// float = float/int
		d=1.0/3.0;						// float = f/f
		e=(float) 1/3;
		f=(float) (1/3);					// () parantez öncelikli!! tip zorlamasý aka type-casting
		
		printf("a=%f \n",a);
		printf("b=%f \n",b);
		printf("c=%f \n",c);
		printf("d=%f \n",d);
		printf("e=%f \n",e);
		printf("f=%f \n",f);
		
		getch();
		return 0;

}

*/

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/*

#include<stdio.h>
#include<conio.h>
															//WHÝLE DÖNGÜSÜ
int main(){

	int x;
	
	x=0;

	while(x<=50){
		printf("%d Beykent \n",x);
		
		x++;
	}
getch();
return 0;
}

*/

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/*
#include<stdio.h>
#include<conio.h>
															//DO DÖNGÜSÜ //düzelt!
int main(){

	int x;
	x=100;
		while(x<=0);
		do{printf("%d Beykent \n",x)
		 x++;
		  while(x<=0);
		
		
		}
}
*/

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/*
#include<stdio.h>
#include<conio.h>
															//FOR DÖNGÜSÜ
int main(){

int i;

for(i=0; i<=50; i++) { printf("%d Beykent \n",i); }
	
		

}
*/
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 3.hafta
/*
#include<stdio.h>
#include<conio.h>		//hipotenus bulma

float hypo(float,float);

int main(){
	float x,y,z;

printf("1.kenari giriniz...\n");
scanf("%f",&x);
printf("2.kenari giriniz...\n");
scanf("%f",&y);

z=hypo(x,y);
printf("Hipotenus %f uzunluğunda\n",z);



}

float hypo(float a, float b){
	return(sqrt(a*a + b*b))
}
*/














