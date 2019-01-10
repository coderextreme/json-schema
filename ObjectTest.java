/*
 * Copyright (C) 2011 Everit Kft. (http://www.everit.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.everit.json.schema.loader.SchemaLoader;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.Schema;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONException;

import java.io.*;
import java.util.*;

public class ObjectTest {
    public static void main(String args[]) {
    	for (int a = 0; a < args.length; a++) {
		String version = "Unknown";
		try {
			JSONTokener tokener = new JSONTokener(new FileInputStream(args[a]));
			JSONObject jsonSubject = new JSONObject(tokener);
			version = jsonSubject.getJSONObject("X3D").getString("@version");
			JSONObject jsonSchema = new JSONObject(new JSONTokener(
				ObjectTest.class
					.getResourceAsStream("x3d-"+version+"-JSONSchema.json")));

			Schema schema = SchemaLoader.load(jsonSchema);
			schema.validate(jsonSubject);
			
			// System.out.println("json-schema "+version+" Valid "+args[a]);
		} catch (NullPointerException npe) {
			System.out.println("json-config "+version+" null point error "+args[a]);
		} catch (FileNotFoundException e) {
			System.out.println("json-file file missing "+e.getMessage()+" "+args[a]);
		} catch (NumberFormatException nfe) {
			System.out.println("json-parse json "+nfe.getMessage()+" "+args[a]);
		} catch (JSONException je) {
			System.out.println("json-parse json "+je.getMessage()+" "+args[a]);
		} catch (ValidationException ve) {
			Iterator<ValidationException> i = ve.getCausingExceptions().iterator();
			while (i.hasNext()) {
				ValidationException veel = i.next();
				System.out.println("json-schema "+version+" Validation error "+veel+" "+args[a]);
			}
		}
	}
    }
}
