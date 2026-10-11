package dev.lokspel.practice.data;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.Deserializers;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;

/**
 * Reads and writes Bukkit objects ({@link org.bukkit.inventory.ItemStack}, {@link
 * org.bukkit.Location}, ...) the way Bukkit's {@code YamlConfiguration} does: as a map of their
 * serialized values with the {@code ==} key naming the type. Data files written by earlier versions
 * of the plugin therefore load unchanged.
 */
final class BukkitModule extends SimpleModule {
  private static final String TYPE_KEY = ConfigurationSerialization.SERIALIZED_TYPE_KEY;

  BukkitModule() {
    super("AstralPracticeBukkit");
    addSerializer(ConfigurationSerializable.class, new Serializer());
  }

  @Override
  public void setupModule(SetupContext context) {
    super.setupModule(context);
    context.addDeserializers(new DeserializerLookup());
  }

  private static final class Serializer extends StdSerializer<ConfigurationSerializable> {
    private Serializer() {
      super(ConfigurationSerializable.class);
    }

    @Override
    public void serialize(
        ConfigurationSerializable value, JsonGenerator generator, SerializerProvider provider)
        throws IOException {
      generator.writeStartObject();
      generator.writeStringField(TYPE_KEY, ConfigurationSerialization.getAlias(value.getClass()));
      for (Map.Entry<String, Object> entry : value.serialize().entrySet()) {
        generator.writeFieldName(entry.getKey());
        provider.defaultSerializeValue(entry.getValue(), generator);
      }
      generator.writeEndObject();
    }
  }

  private static final class DeserializerLookup extends Deserializers.Base {
    @Override
    public JsonDeserializer<?> findBeanDeserializer(
        JavaType type, DeserializationConfig config, BeanDescription description) {
      Class<?> raw = type.getRawClass();
      if (!ConfigurationSerializable.class.isAssignableFrom(raw)) {
        return null;
      }

      return new Deserializer(raw.asSubclass(ConfigurationSerializable.class));
    }
  }

  private static final class Deserializer extends JsonDeserializer<ConfigurationSerializable> {
    private final Class<? extends ConfigurationSerializable> type;

    private Deserializer(Class<? extends ConfigurationSerializable> type) {
      this.type = type;
    }

    @Override
    public ConfigurationSerializable deserialize(JsonParser parser, DeserializationContext context)
        throws IOException {
      Object value = revive(parser.readValueAs(Object.class));
      if (type.isInstance(value)) {
        return type.cast(value);
      }
      if (value instanceof Map<?, ?> map) {
        return ConfigurationSerialization.deserializeObject(strings(map), type);
      }

      return context.reportInputMismatch(this, "Expected a serialized %s", type.getSimpleName());
    }

    /** Turns maps naming a Bukkit type into that object, innermost first, as Bukkit does. */
    private static Object revive(Object value) {
      if (value instanceof List<?> list) {
        List<Object> revived = new ArrayList<>(list.size());
        for (Object element : list) {
          revived.add(revive(element));
        }
        return revived;
      }
      if (!(value instanceof Map<?, ?> map)) {
        return value;
      }

      Map<String, Object> revived = new LinkedHashMap<>();
      for (Map.Entry<?, ?> entry : map.entrySet()) {
        revived.put(String.valueOf(entry.getKey()), revive(entry.getValue()));
      }
      if (!revived.containsKey(TYPE_KEY)) {
        return revived;
      }

      ConfigurationSerializable object = ConfigurationSerialization.deserializeObject(revived);
      return object != null ? object : revived;
    }

    private static Map<String, Object> strings(Map<?, ?> map) {
      Map<String, Object> result = new LinkedHashMap<>();
      map.forEach((key, entry) -> result.put(String.valueOf(key), entry));
      return result;
    }
  }
}
