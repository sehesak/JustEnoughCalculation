package me.towdium.jecalculation.data.structure;

import me.towdium.jecalculation.data.label.ILabel;
import me.towdium.jecalculation.utils.IllegalPositionException;
import me.towdium.jecalculation.utils.wrappers.Single;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Author: towdium
 * Date:   17-10-6.
 */
public class Recipe {
    public static final String KEY_INPUT = "input";
    public static final String KEY_CATALYST = "catalyst";
    public static final String KEY_OUTPUT = "output";
    static final ILabel[] EMPTY_ARRAY = new ILabel[0];
    ILabel[] input;
    ILabel[] catalyst;
    ILabel[] output;
    int hashcode;

    public Recipe(NBTTagCompound nbt) {
        this(readNbtList(nbt.getTagList(KEY_INPUT, 10)),
                readNbtList(nbt.getTagList(KEY_CATALYST, 10)),
                readNbtList(nbt.getTagList(KEY_OUTPUT, 10)));
    }

    public Recipe(List<ILabel> input, List<ILabel> catalyst, List<ILabel> output) {
        this(input.toArray(EMPTY_ARRAY), catalyst.toArray(EMPTY_ARRAY), output.toArray(EMPTY_ARRAY));
    }

    public Recipe(ILabel[] input, ILabel[] catalyst, ILabel[] output) {
        BiFunction<ILabel[], Integer, ILabel[]> convert = (ls, i) -> {
            ILabel[] ret = new ILabel[i];
            if (ls.length > i) throw new RuntimeException("Too many labels");
            System.arraycopy(ls, 0, this.input, 0, Math.min(i, ls.length));
            return ret;
        };
        this.input = convert.apply(input, 16);
        this.catalyst = convert.apply(catalyst, 8);
        this.output = convert.apply(output, 8);

        Single<Integer> hash = new Single<>(0);
        Consumer<ILabel[]> hasher = (ls) -> Arrays.stream(ls)
                .filter(Objects::nonNull).forEach(i -> hash.value ^= i.hashCode());
        hasher.accept(input);
        hasher.accept(catalyst);
        hasher.accept(output);
        hashcode = hash.value;
    }

    static private List<ILabel> readNbtList(NBTTagList list) {
        return StreamSupport.stream(list.spliterator(), false)
                .filter(n -> n instanceof NBTTagCompound)
                .map(n -> ILabel.DESERIALIZER.deserialize((NBTTagCompound) n))
                .collect(Collectors.toList());
    }

    @Override
    public int hashCode() {
        return hashcode;
    }

    public ILabel[] getLabel(enumIoType type) {
        switch (type) {
            case INPUT:
                return input;
            case OUTPUT:
                return output;
            case CATALYST:
                return catalyst;
            default:
                throw new IllegalPositionException();
        }
    }

    public NBTTagCompound serialize() {
        NBTTagCompound ret = new NBTTagCompound();
        Function<ILabel[], NBTTagList> convert = (ls) -> {
            NBTTagList r = new NBTTagList();
            Arrays.stream(ls).forEach(l -> r.appendTag(ILabel.DESERIALIZER.serialize(l)));
            return r;
        };
        ret.setTag(KEY_INPUT, convert.apply(input));
        ret.setTag(KEY_CATALYST, convert.apply(catalyst));
        ret.setTag(KEY_OUTPUT, convert.apply(output));
        return ret;
    }

    public enum enumIoType {
        INPUT, OUTPUT, CATALYST;

        public int getSize() {
            return this == INPUT ? 16 : 8;
        }
    }
}
