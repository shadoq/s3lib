#if defined(diffuseTextureFlag) || defined(specularTextureFlag)
	#define textureFlag
#endif

#if defined(specularTextureFlag) || defined(specularColorFlag)
	#define specularFlag
#endif

#if defined(specularFlag) || defined(fogFlag)
	#define cameraPositionFlag
#endif

uniform mat4 u_projTrans;
uniform mat4 u_worldTrans;
attribute vec3 a_position;
varying vec4 v_position;

#if defined(colorFlag)
	varying vec4 v_color;
	attribute vec4 a_color;
#endif

#ifdef normalFlag
	attribute vec3 a_normal;
	uniform mat3 u_normalMatrix;
	varying vec3 v_normal;
#endif

#ifdef textureFlag
	attribute vec2 a_texCoord0;
	varying vec2 v_texCoords0;
#endif

//----------------------------------------------
// Bone
//----------------------------------------------
#ifdef boneWeight0Flag
	#define boneWeightsFlag
	attribute vec2 a_boneWeight0;
#endif

#ifdef boneWeight1Flag
	#ifndef boneWeightsFlag
		#define boneWeightsFlag
	#endif
attribute vec2 a_boneWeight1;
#endif

#ifdef boneWeight2Flag
	#ifndef boneWeightsFlag
		#define boneWeightsFlag
	#endif
	attribute vec2 a_boneWeight2;
#endif

#ifdef boneWeight3Flag
	#ifndef boneWeightsFlag
		#define boneWeightsFlag
	#endif
	attribute vec2 a_boneWeight3;
#endif

#ifdef boneWeight4Flag
	#ifndef boneWeightsFlag
		#define boneWeightsFlag
	#endif
	attribute vec2 a_boneWeight4;
#endif

#ifdef boneWeight5Flag
	#ifndef boneWeightsFlag
		#define boneWeightsFlag
	#endif
	attribute vec2 a_boneWeight5;
#endif

#ifdef boneWeight6Flag
	#ifndef boneWeightsFlag
		#define boneWeightsFlag
	#endif
	attribute vec2 a_boneWeight6;
#endif

#ifdef boneWeight7Flag
	#ifndef boneWeightsFlag
		#define boneWeightsFlag
	#endif
	attribute vec2 a_boneWeight7;
#endif

#if defined(numBones) && defined(boneWeightsFlag)
	#if (numBones > 0) 
		#define skinningFlag
	#endif
#endif

#if defined(numBones)
	#if numBones > 0
		uniform mat4 u_bones[numBones];
	#endif
#endif

//----------------------------------------------
// Blend
//----------------------------------------------
#ifdef blendedFlag
		uniform float u_opacity;
		varying float v_opacity;
	#ifdef alphaTestFlag
		uniform float u_alphaTest;
		varying float v_alphaTest;
	#endif
#endif

//----------------------------------------------
// Lighting
//----------------------------------------------
#ifdef shininessFlag
		uniform float u_shininess;
	#else
		const float u_shininess = 20.0;
#endif

#ifdef cameraPositionFlag
	uniform vec4 u_cameraPosition;
	varying vec4 v_cameraPosition;
#endif

#ifdef fogFlag
	varying float v_fog;
#endif

void main() {
	#ifdef textureFlag
		v_texCoords0 = a_texCoord0;
	#endif
	
	#if defined(colorFlag)
		v_color = a_color;
	#endif
		
	#ifdef blendedFlag
		v_opacity = u_opacity;
		#ifdef alphaTestFlag
			v_alphaTest = u_alphaTest;
		#endif
	#endif
	
	#ifdef skinningFlag
		mat4 skinning = mat4(0.0);
		#ifdef boneWeight0Flag
			skinning += (a_boneWeight0.y) * u_bones[int(a_boneWeight0.x)];
		#endif 
		#ifdef boneWeight1Flag				
			skinning += (a_boneWeight1.y) * u_bones[int(a_boneWeight1.x)];
		#endif 
		#ifdef boneWeight2Flag		
			skinning += (a_boneWeight2.y) * u_bones[int(a_boneWeight2.x)];
		#endif 
		#ifdef boneWeight3Flag
			skinning += (a_boneWeight3.y) * u_bones[int(a_boneWeight3.x)];
		#endif 
		#ifdef boneWeight4Flag
			skinning += (a_boneWeight4.y) * u_bones[int(a_boneWeight4.x)];
		#endif 
		#ifdef boneWeight5Flag
			skinning += (a_boneWeight5.y) * u_bones[int(a_boneWeight5.x)];
		#endif 
		#ifdef boneWeight6Flag
			skinning += (a_boneWeight6.y) * u_bones[int(a_boneWeight6.x)];
		#endif 
		#ifdef boneWeight7Flag
			skinning += (a_boneWeight7.y) * u_bones[int(a_boneWeight7.x)];
		#endif 
	#endif

	#ifdef skinningFlag
		vec4 position = u_worldTrans * skinning * vec4(a_position, 1.0);
	#else
		vec4 position = u_worldTrans * vec4(a_position, 1.0);
	#endif
	
	v_position=position;
	gl_Position = u_projTrans * position;
	
	#if defined(normalFlag)
		#if defined(skinningFlag)
			vec3 normal = normalize((u_worldTrans * skinning * vec4(a_normal, 0.0)).xyz);
		#else
			vec3 normal = normalize(u_normalMatrix * a_normal);
		#endif
		v_normal = normal;
	#endif

    #ifdef cameraPositionFlag
        v_cameraPosition = u_cameraPosition;
    #endif

    #ifdef fogFlag
        vec3 flen = u_cameraPosition.xyz - position.xyz;
        float fog = dot(flen, flen) * u_cameraPosition.w;
        v_fog = min(fog, 1.0);
    #endif
}
