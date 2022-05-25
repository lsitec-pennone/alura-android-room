package br.com.alura.agenda.database.converter;

import androidx.room.TypeConverter;

import java.util.Calendar;

// classe para converter o tipo Calendar em um tipo reconhecido pelo database
public class ConversorCalendar {

    // @TypeConverter para indicar que é um método de conversão
    @TypeConverter
    // método que converde o tipo Calendar em tipo Long
    public Long paraLong(Calendar valor){
        if(valor != null) {
            return valor.getTimeInMillis();
        }
        return null;
    }

    // @TypeConverter para indicar que é um método de conversão
    @TypeConverter
    // método que converde o tipo Long em tipo Calendar
    public Calendar paraCalendar(Long valor){
        Calendar momentoAtual = Calendar.getInstance();
        if(valor != null){
            momentoAtual.setTimeInMillis(valor);
        }
        return momentoAtual;
    }

}
