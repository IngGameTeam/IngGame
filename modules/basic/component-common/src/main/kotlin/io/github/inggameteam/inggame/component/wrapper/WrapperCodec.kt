package io.github.inggameteam.inggame.component.wrapper

import io.github.inggameteam.inggame.utils.NoArgsConstructor
import org.bson.BsonReader
import org.bson.BsonWriter
import org.bson.codecs.Codec
import org.bson.codecs.DecoderContext
import org.bson.codecs.EncoderContext
import org.koin.ext.getFullName

@NoArgsConstructor
class WrapperCodec : Codec<WrapperModel> {
    override fun encode(writer: BsonWriter?, value: WrapperModel?, encoderContext: EncoderContext?) {
        if (value != null && writer != null) {
            writer.writeStartDocument()
            writer.writeString("_t", value::class.getFullName())
            writer.writeString(WrapperModel::componentService.name, value.componentService)
            writer.writeString(WrapperModel::nameSpace.name, value.nameSpace)
            writer.writeString(WrapperModel::wrapperClass.name, value.wrapperClass)
            writer.writeEndDocument()
        }
    }

    override fun getEncoderClass() = WrapperModel::class.java

    override fun decode(reader: BsonReader?, decoderContext: DecoderContext?): WrapperModel {
        if (reader != null) {
            reader.readStartDocument()
            val result = WrapperModel(
                reader.readString(WrapperModel::componentService.name),
                reader.readString(WrapperModel::nameSpace.name),
                reader.readString(WrapperModel::wrapperClass.name)
            )
            reader.readEndDocument()
            return result
        }
        throw AssertionError("an error occurred while decoding WrapperModel")
    }
}