package br.com.alura.agenda.database.converter;

import androidx.room.TypeConverter;

import br.com.alura.agenda.model.TipoTelefone;

// classe para converter o tipo TipoTelefone em um tipo reconhecido pelo database
public class ConversorTipoTelefone {

    // @TypeConverter para indicar que é um método de conversão
    @TypeConverter
    // método que converde o tipo Calendar em tipo Long
    public String paraString(TipoTelefone tipo) {
        return tipo.name();
    }

    // @TypeConverter para indicar que é um método de conversão
    @TypeConverter
    // método que converde o tipo Long em tipo Calendar
    public TipoTelefone paraTipoTelefone(String valor){
        if(valor != null){
            return TipoTelefone.valueOf(valor);
        }
        return TipoTelefone.FIXO;
    }
}
