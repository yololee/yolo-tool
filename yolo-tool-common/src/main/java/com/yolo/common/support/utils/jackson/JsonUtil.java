package com.yolo.common.support.utils.jackson;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.type.ArrayType;
import com.fasterxml.jackson.databind.type.CollectionLikeType;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;
import com.yolo.common.support.function.CheckedConsumer;
import com.yolo.common.support.utils.exception.ExceptionUtil;
import com.yolo.common.support.utils.string.StringUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.*;

/**
 * Jackson工具类
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonUtil {

    //======================================to json Str ================================================================

    /**
     * 将对象序列化成json字符串
     *
     * @param value javaBean
     * @param <T>   T 泛型标记
     * @return jsonString json字符串
     */
    public static <T> String toJson(@Nullable T value) {
        try {
            return getInstance().writeValueAsString(value);
        } catch (Exception e) {
            throw ExceptionUtil.unchecked(e);
        }
    }

    /**
     * 将对象序列化成 json 字符串，格式美化
     *
     * @param object javaBean
     * @return jsonString json字符串
     */
    @Nullable
    public static String toPrettyJson(@Nullable Object object) {
        if (object == null) {
            return null;
        }
        try {
            return getInstance()
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw ExceptionUtil.unchecked(e);
        }
    }

    /**
     * 将对象序列化成 json byte 数组
     *
     * @param object javaBean
     * @return jsonString json字符串
     */
    public static byte[] toJsonAsBytes(@Nullable Object object) {
        try {
            return getInstance().writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            throw ExceptionUtil.unchecked(e);
        }
    }


    //======================================parse json Str  to bean====================================================

    /**
     * 将json反序列化成对象
     *
     * @param content   content
     * @param valueType class
     * @param <T>       T 泛型标记
     * @return Bean
     */
    public static <T> T parse(@Nullable String content, Class<T> valueType) {
        try {
            return getInstance().readValue(content, valueType);
        } catch (Exception e) {
            throw ExceptionUtil.unchecked(e);
        }
    }

    /**
     * 将json反序列化成对象
     *
     * @param content       content
     * @param typeReference 泛型类型
     * @param <T>           T 泛型标记
     * @return Bean
     */
    public static <T> T parse(@Nullable String content, TypeReference<T> typeReference) {
        try {
            return getInstance().readValue(content, typeReference);
        } catch (IOException e) {
            throw ExceptionUtil.unchecked(e);
        }
    }

    /**
     * 将json byte 数组反序列化成对象
     *
     * @param bytes     json bytes
     * @param valueType class
     * @param <T>       T 泛型标记
     * @return Bean
     */
    public static <T> T parse(@Nullable byte[] bytes, Class<T> valueType) {
        try {
            return getInstance().readValue(bytes, valueType);
        } catch (IOException e) {
            throw ExceptionUtil.unchecked(e);
        }
    }


    /**
     * 将json反序列化成对象
     *
     * @param bytes         bytes
     * @param typeReference 泛型类型
     * @param <T>           T 泛型标记
     * @return Bean
     */
    public static <T> T parse(@Nullable byte[] bytes, TypeReference<T> typeReference) {
        try {
            return getInstance().readValue(bytes, typeReference);
        } catch (IOException e) {
            throw ExceptionUtil.unchecked(e);
        }
    }

    /**
     * 将json反序列化成对象
     *
     * @param in        InputStream
     * @param valueType class
     * @param <T>       T 泛型标记
     * @return Bean
     */
    public static <T> T parse(@Nullable InputStream in, Class<T> valueType) {
        try {
            return getInstance().readValue(in, valueType);
        } catch (IOException e) {
            throw ExceptionUtil.unchecked(e);
        }
    }

    /**
     * 将json反序列化成对象
     *
     * @param in            InputStream
     * @param typeReference 泛型类型
     * @param <T>           T 泛型标记
     * @return Bean
     */
    public static <T> T parse(@Nullable InputStream in, TypeReference<T> typeReference) {
        try {
            return getInstance().readValue(in, typeReference);
        } catch (IOException e) {
            throw ExceptionUtil.unchecked(e);
        }
    }



    //======================================parse json Str  to collection====================================================

    /**
     * json转List
     *
     * @param json        json
     * @param resultClazz 结果clazz
     * @return {@link Set}<{@link T}>
     */
    public static <T> Set<T> jsonToList(@Nullable String json, Class<T> resultClazz) {
        try {
            CollectionType setType = getInstance().getTypeFactory().constructCollectionType(ArrayList.class, resultClazz);
            return getInstance().readValue(json, setType);
        } catch (IOException e) {
            throw ExceptionUtil.unchecked(e);
        }
    }

    /**
     * json转Set
     *
     * @param json        json
     * @param resultClazz 结果clazz
     * @return {@link Set}<{@link T}>
     */
    public static <T> Set<T> jsonToSet(@Nullable String json, Class<T> resultClazz) {
        try {
            CollectionType setType = getInstance().getTypeFactory().constructCollectionType(HashSet.class, resultClazz);
            return getInstance().readValue(json, setType);
        } catch (IOException e) {
            throw ExceptionUtil.unchecked(e);
        }
    }


    /**
     * json转Arr
     *
     * @param json        json
     * @param resultClazz 结果clazz
     * @return {@link T[]}
     */
    public static <T> T[] jsonToArray(@Nullable String json, Class<T> resultClazz) {
        try {
            ArrayType arrayType = getInstance().getTypeFactory().constructArrayType(resultClazz);
            return getInstance().readValue(json, arrayType);
        } catch (IOException e) {
            throw ExceptionUtil.unchecked(e);
        }
    }

    /**
     * bean转map
     *
     * @param obj obj
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    public static Map<String, Object> beanToMap(@Nullable Object obj) {
        Map<String, Object> map = new HashMap<>(16);
        try {
            if (Objects.nonNull(obj)) {
                Field[] fields = obj.getClass().getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    map.put(field.getName(), field.get(obj));
                }
            }
        } catch (Exception e) {
            throw ExceptionUtil.unchecked(e);
        }
        return map;
    }


    public static <T> T mapToBean(@Nullable Map fromValue, Class<T> toValueType) {
        return getInstance().convertValue(fromValue, toValueType);
    }

    /**
     * 将json字符串转成 JsonNode
     *
     * @param jsonString jsonString
     * @return jsonString json字符串
     */
    public static JsonNode readTree(String jsonString) {
        try {
            return getInstance().readTree(jsonString);
        } catch (IOException e) {
            throw ExceptionUtil.unchecked(e);
        }
    }

    /**
     * 将json字符串转成 JsonNode
     *
     * @param in InputStream
     * @return jsonString json字符串
     */
    public static JsonNode readTree(InputStream in) {
        try {
            return getInstance().readTree(in);
        } catch (IOException e) {
            throw ExceptionUtil.unchecked(e);
        }
    }

    /**
     * 将json字符串转成 JsonNode
     *
     * @param content content
     * @return jsonString json字符串
     */
    public static JsonNode readTree(byte[] content) {
        try {
            return getInstance().readTree(content);
        } catch (IOException e) {
            throw ExceptionUtil.unchecked(e);
        }
    }


    /**
     * 将json byte 数组反序列化成对象
     *
     * @param content   json bytes
     * @param valueType class
     * @param <T>       T 泛型标记
     * @return Bean
     */
    @Nullable
    public static <T> T readValue(@Nullable byte[] content, Class<T> valueType) {
        try {
            return getInstance().readValue(content, valueType);
        } catch (IOException e) {
            throw ExceptionUtil.unchecked(e);
        }
    }

    /**
     * 将json反序列化成对象
     *
     * @param jsonString jsonString
     * @param valueType  class
     * @param <T>        T 泛型标记
     * @return Bean
     */
    @Nullable
    public static <T> T readValue(@Nullable String jsonString, Class<T> valueType) {
        try {
            return getInstance().readValue(jsonString, valueType);
        } catch (IOException e) {
            throw ExceptionUtil.unchecked(e);
        }
    }

    /**
     * 将json反序列化成对象
     *
     * @param in        InputStream
     * @param valueType class
     * @param <T>       T 泛型标记
     * @return Bean
     */
    @Nullable
    public static <T> T readValue(@Nullable InputStream in, Class<T> valueType) {
        try {
            return getInstance().readValue(in, valueType);
        } catch (IOException e) {
            throw ExceptionUtil.unchecked(e);
        }
    }

    /**
     * 将json反序列化成对象
     *
     * @param content       bytes
     * @param typeReference 泛型类型
     * @param <T>           T 泛型标记
     * @return Bean
     */
    @Nullable
    public static <T> T readValue(@Nullable byte[] content, TypeReference<T> typeReference) {
        try {
            return getInstance().readValue(content, typeReference);
        } catch (IOException e) {
            throw ExceptionUtil.unchecked(e);
        }
    }

    /**
     * 将json反序列化成对象
     *
     * @param jsonString    jsonString
     * @param typeReference 泛型类型
     * @param <T>           T 泛型标记
     * @return Bean
     */
    @Nullable
    public static <T> T readValue(@Nullable String jsonString, TypeReference<T> typeReference) {
        if (StringUtil.isBlank(jsonString)) {
            return null;
        }
        try {
            return getInstance().readValue(jsonString, typeReference);
        } catch (IOException e) {
            throw ExceptionUtil.unchecked(e);
        }
    }

    /**
     * 将json反序列化成对象
     *
     * @param in            InputStream
     * @param typeReference 泛型类型
     * @param <T>           T 泛型标记
     * @return Bean
     */
    @Nullable
    public static <T> T readValue(@Nullable InputStream in, TypeReference<T> typeReference) {
        try {
            return getInstance().readValue(in, typeReference);
        } catch (IOException e) {
            throw ExceptionUtil.unchecked(e);
        }
    }

    /**
     * 读取集合
     *
     * @param content      bytes
     * @param elementClass elementClass
     * @param <T>          泛型
     * @return 集合
     */
    public static <T> List<T> readList(@Nullable byte[] content, Class<T> elementClass) {
        if (ObjectUtils.isEmpty(content)) {
            return Collections.emptyList();
        }
        try {
            return getInstance().readValue(content, getListType(elementClass));
        } catch (IOException e) {
            throw ExceptionUtil.unchecked(e);
        }
    }

    /**
     * 读取集合
     *
     * @param content      InputStream
     * @param elementClass elementClass
     * @param <T>          泛型
     * @return 集合
     */
    public static <T> List<T> readList(@Nullable InputStream content, Class<T> elementClass) {
        if (content == null) {
            return Collections.emptyList();
        }
        try {
            return getInstance().readValue(content, getListType(elementClass));
        } catch (IOException e) {
            throw ExceptionUtil.unchecked(e);
        }
    }

    /**
     * 读取集合
     *
     * @param reader       java.io.Reader
     * @param elementClass elementClass
     * @param <T>          泛型
     * @return 集合
     */
    public static <T> List<T> readList(@Nullable Reader reader, Class<T> elementClass) {
        if (reader == null) {
            return Collections.emptyList();
        }
        try {
            return getInstance().readValue(reader, getListType(elementClass));
        } catch (IOException e) {
            throw ExceptionUtil.unchecked(e);
        }
    }

    /**
     * 读取集合
     *
     * @param content      bytes
     * @param elementClass elementClass
     * @param <T>          泛型
     * @return 集合
     */
    public static <T> List<T> readList(@Nullable String content, Class<T> elementClass) {
        if (StringUtil.isBlank(content)) {
            return Collections.emptyList();
        }
        try {
            return getInstance().readValue(content, getListType(elementClass));
        } catch (IOException e) {
            throw ExceptionUtil.unchecked(e);
        }
    }

    /**
     * 封装 map type
     *
     * @param elementClass 集合值类型
     * @return CollectionLikeType
     */
    public static CollectionLikeType getListType(Class<?> elementClass) {
        return getInstance().getTypeFactory().constructCollectionLikeType(List.class, elementClass);
    }

    /**
     * 封装 map type，keyClass String
     *
     * @param valueClass value 类型
     * @return MapType
     */
    public static MapType getMapType(Class<?> valueClass) {
        return getMapType(String.class, valueClass);
    }

    /**
     * 封装 map type
     *
     * @param keyClass   key 类型
     * @param valueClass value 类型
     * @return MapType
     */
    public static MapType getMapType(Class<?> keyClass, Class<?> valueClass) {
        return getInstance().getTypeFactory().constructMapType(Map.class, keyClass, valueClass);
    }

    /**
     * 读取集合
     *
     * @param content bytes
     * @return 集合
     */
    public static Map<String, Object> readMap(@Nullable byte[] content) {
        return readMap(content, Object.class);
    }

    /**
     * 读取集合
     *
     * @param content InputStream
     * @return 集合
     */
    public static Map<String, Object> readMap(@Nullable InputStream content) {
        return readMap(content, Object.class);
    }

    /**
     * 读取集合
     *
     * @param reader java.io.Reader
     * @return 集合
     */
    public static Map<String, Object> readMap(@Nullable Reader reader) {
        return readMap(reader, Object.class);
    }

    /**
     * 读取集合
     *
     * @param content bytes
     * @return 集合
     */
    public static Map<String, Object> readMap(@Nullable String content) {
        return readMap(content, Object.class);
    }

    /**
     * 读取集合
     *
     * @param content    bytes
     * @param valueClass 值类型
     * @param <V>        泛型
     * @return 集合
     */
    public static <V> Map<String, V> readMap(@Nullable byte[] content, Class<?> valueClass) {
        return readMap(content, String.class, valueClass);
    }

    /**
     * 读取集合
     *
     * @param content    InputStream
     * @param valueClass 值类型
     * @param <V>        泛型
     * @return 集合
     */
    public static <V> Map<String, V> readMap(@Nullable InputStream content, Class<?> valueClass) {
        return readMap(content, String.class, valueClass);
    }

    /**
     * 读取集合
     *
     * @param reader     java.io.Reader
     * @param valueClass 值类型
     * @param <V>        泛型
     * @return 集合
     */
    public static <V> Map<String, V> readMap(@Nullable Reader reader, Class<?> valueClass) {
        return readMap(reader, String.class, valueClass);
    }

    /**
     * 读取集合
     *
     * @param content    bytes
     * @param valueClass 值类型
     * @param <V>        泛型
     * @return 集合
     */
    public static <V> Map<String, V> readMap(@Nullable String content, Class<?> valueClass) {
        return readMap(content, String.class, valueClass);
    }

    /**
     * 读取集合
     *
     * @param content    bytes
     * @param keyClass   key类型
     * @param valueClass 值类型
     * @param <K>        泛型
     * @param <V>        泛型
     * @return 集合
     */
    public static <K, V> Map<K, V> readMap(@Nullable byte[] content, Class<?> keyClass, Class<?> valueClass) {
        if (ObjectUtils.isEmpty(content)) {
            return Collections.emptyMap();
        }
        try {
            return getInstance().readValue(content, getMapType(keyClass, valueClass));
        } catch (IOException e) {
            throw ExceptionUtil.unchecked(e);
        }
    }

    /**
     * 读取集合
     *
     * @param content    InputStream
     * @param keyClass   key类型
     * @param valueClass 值类型
     * @param <K>        泛型
     * @param <V>        泛型
     * @return 集合
     */
    public static <K, V> Map<K, V> readMap(@Nullable InputStream content, Class<?> keyClass, Class<?> valueClass) {
        if (content == null) {
            return Collections.emptyMap();
        }
        try {
            return getInstance().readValue(content, getMapType(keyClass, valueClass));
        } catch (IOException e) {
            throw ExceptionUtil.unchecked(e);
        }
    }

    /**
     * 读取集合
     *
     * @param reader     java.io.Reader
     * @param keyClass   key类型
     * @param valueClass 值类型
     * @param <K>        泛型
     * @param <V>        泛型
     * @return 集合
     */
    public static <K, V> Map<K, V> readMap(@Nullable Reader reader, Class<?> keyClass, Class<?> valueClass) {
        if (reader == null) {
            return Collections.emptyMap();
        }
        try {
            return getInstance().readValue(reader, getMapType(keyClass, valueClass));
        } catch (IOException e) {
            throw ExceptionUtil.unchecked(e);
        }
    }

    /**
     * 读取集合
     *
     * @param content    bytes
     * @param keyClass   key类型
     * @param valueClass 值类型
     * @param <K>        泛型
     * @param <V>        泛型
     * @return 集合
     */
    public static <K, V> Map<K, V> readMap(@Nullable String content, Class<?> keyClass, Class<?> valueClass) {
        if (StringUtil.isBlank(content)) {
            return Collections.emptyMap();
        }
        try {
            return getInstance().readValue(content, getMapType(keyClass, valueClass));
        } catch (IOException e) {
            throw ExceptionUtil.unchecked(e);
        }
    }

    /**
     * jackson 的类型转换
     *
     * @param fromValue   来源对象
     * @param toValueType 转换的类型
     * @param <T>         泛型标记
     * @return 转换结果
     */
    public static <T> T convertValue(Object fromValue, Class<T> toValueType) {
        return getInstance().convertValue(fromValue, toValueType);
    }

    /**
     * jackson 的类型转换
     *
     * @param fromValue   来源对象
     * @param toValueType 转换的类型
     * @param <T>         泛型标记
     * @return 转换结果
     */
    public static <T> T convertValue(Object fromValue, JavaType toValueType) {
        return getInstance().convertValue(fromValue, toValueType);
    }

    /**
     * jackson 的类型转换
     *
     * @param fromValue      来源对象
     * @param toValueTypeRef 泛型类型
     * @param <T>            泛型标记
     * @return 转换结果
     */
    public static <T> T convertValue(Object fromValue, TypeReference<T> toValueTypeRef) {
        return getInstance().convertValue(fromValue, toValueTypeRef);
    }

    /**
     * tree 转对象
     *
     * @param treeNode  TreeNode
     * @param valueType valueType
     * @param <T>       泛型标记
     * @return 转换结果
     */
    public static <T> T treeToValue(TreeNode treeNode, Class<T> valueType) {
        try {
            return getInstance().treeToValue(treeNode, valueType);
        } catch (JsonProcessingException e) {
            throw ExceptionUtil.unchecked(e);
        }
    }

    /**
     * 对象转 tree
     *
     * @param fromValue fromValue
     * @param <T>       泛型标记
     * @return 转换结果
     */
    public static <T extends JsonNode> T valueToTree(@Nullable Object fromValue) {
        return getInstance().valueToTree(fromValue);
    }

    /**
     * 判断是否可以序列化
     *
     * @param value 对象
     * @return 是否可以序列化
     */
    public static boolean canSerialize(@Nullable Object value) {
        if (value == null) {
            return true;
        }
        return getInstance().canSerialize(value.getClass());
    }

    /**
     * 检验 json 格式
     *
     * @param jsonString json 字符串
     * @return 是否成功
     */
    public static boolean isValidJson(String jsonString) {
        return isValidJson(mapper -> {
            assert mapper != null;
            mapper.readTree(jsonString);
        });
    }


    /**
     * 检验 json 格式
     *
     * @param consumer ObjectMapper consumer
     * @return 是否成功
     */
    public static boolean isValidJson(CheckedConsumer<ObjectMapper> consumer) {
        ObjectMapper mapper = getInstance().copy();
        mapper.enable(DeserializationFeature.FAIL_ON_TRAILING_TOKENS);
        mapper.enable(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY);
        try {
            consumer.accept(mapper);
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    /**
     * 创建 ObjectNode
     *
     * @return ObjectNode
     */
    public static ObjectNode createObjectNode() {
        return getInstance().createObjectNode();
    }

    /**
     * 创建 ArrayNode
     *
     * @return ArrayNode
     */
    public static ArrayNode createArrayNode() {
        return getInstance().createArrayNode();
    }


    /**
     * 将json字符串转成 JsonNode
     *
     * @param jsonParser JsonParser
     * @return jsonString json字符串
     */
    public static JsonNode readTree(JsonParser jsonParser) {
        try {
            return getInstance().readTree(jsonParser);
        } catch (IOException e) {
            throw ExceptionUtil.unchecked(e);
        }
    }

    public static ObjectMapper getInstance() {
        return new JacksonObjectMapper();
    }

    public static class JacksonObjectMapper extends ObjectMapper {
        private static final long serialVersionUID = 4288193147502386170L;

        private static final Locale CHINA = Locale.CHINA;

        public JacksonObjectMapper() {
            super();
            //设置地点为中国
            super.setLocale(CHINA);
            //去掉默认的时间戳格式
            super.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            //设置为中国上海时区
            super.setTimeZone(TimeZone.getTimeZone(ZoneId.systemDefault()));
            //序列化时，日期的统一格式
            super.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA));
            //序列化处理
            super.configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true);
            super.configure(JsonReadFeature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER.mappedFeature(), true);
            super.findAndRegisterModules();
            //失败处理
            super.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            super.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            //单引号处理
            super.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
            //反序列化时，属性不存在的兼容处理s
            super.getDeserializationConfig().withoutFeatures(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            //日期格式化
            super.registerModule(new JavaTimeModule());
            super.findAndRegisterModules();
        }

        @Override
        public ObjectMapper copy() {
            return super.copy();
        }
    }

}
