package com.thekillerbunny.goofyplugin.lua;
import java.util.ArrayList;
import org.luaj.vm2.*;
import org.figuramc.figura.lua.*;
import org.figuramc.figura.lua.api.data.FiguraBuffer;

class LuaArray {
	public ArrayList<LuaValue> values;
	@LuaWhitelist
	public int n;
	@LuaWhitelist
	public LuaValue read() {
		return get(n++);
	}
	@LuaWhitelist
	public LuaArray write(Varargs val) {
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
	public LuaArray set(int n, Varargs val) {
		for (int j = 1; j <= val.narg(); n++, j++) {
			if (n >= values.size()) break;
			values.set(n + j - 1, val.arg(j));
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
