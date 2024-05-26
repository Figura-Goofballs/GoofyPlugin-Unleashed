package com.thekillerbunny.goofyplugin.lua;
import java.util.ArrayList;
import org.luaj.vm2.*;
import org.figuramc.figura.lua.*;

@LuaWhitelist
public class LuaArray {
	public final ArrayList<LuaValue> values = new ArrayList<>();
	@LuaWhitelist
	public int n;
	@LuaWhitelist
	public LuaValue read() {
		return get(n++);
	}
	@LuaWhitelist
	public LuaArray write(LuaValue... val) {
		return set(n++, val);
	}
	@LuaWhitelist
	public LuaValue get(int n) {
		if (n < values.size())
			return values.get(n);
		else
			return LuaValue.NIL;
		
	}
	@LuaWhitelist
	public Varargs unpack(int m, int n) {
		if (n > values.size()) n = values.size();
		if (m >= n) return LuaValue.varargsOf(new LuaValue[0]);
		int nn = n; // java pedantry
		return new Varargs() {
			@Override
			public LuaValue arg1() {
				return values.get(nn);
			}	
			@Override
			public LuaValue arg(int i) {
				i += nn - 1;
				return i < m ? values.get(i) : LuaValue.NIL;
			}
			@Override
			public int narg() {
				return m - nn + 1;
			}			
			@Override
			public Varargs subargs(int start) {
				return unpack(nn + start - 1, m);
			}
		};
	}
	@LuaWhitelist
	public LuaArray set(int n, LuaValue... val) {
		for (int j = 0; j <= val.length; n++, j++) {
			if (n > values.size()) throw new LuaError("Out-of-bounds write to index %d of array[%d]".formatted(n, values.size()));
			// automatically expand if writing just one out
			if (n == values.size())
				values.add(val[j]);
			else
				values.set(n, val[j]);
		}
		return this;
	}
	@LuaWhitelist
	public LuaArray __concat(LuaArray b) {
		values.addAll(b.values);
		return this;
	}
	@LuaWhitelist
	public String toString() {
		return "LuaArray" + values.toString();
	}
}
